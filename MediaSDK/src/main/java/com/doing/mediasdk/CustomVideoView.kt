package com.doing.mediasdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.graphics.drawable.AnimationDrawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import org.jetbrains.anko.displayMetrics

class CustomVideoView constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnBufferingUpdateListener, TextureView.SurfaceTextureListener{

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    constructor(context: Context, parentView: ViewGroup) : this(context){
        mParentContainer = parentView
    }

    /**
     * UI
     */
    private val mContext: Context = context
    private var mParentContainer: ViewGroup? = null
    private lateinit var mVideoView: TextureView
    private lateinit var mBtnMiniPlay: Button
    private lateinit var mFullBtn: ImageView
    private lateinit var mLoadingBar: ImageView
    private lateinit var mFrameView: ImageView
    private var mAudioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager //音量控制器
    private var mVideoSurface: Surface? = null //真正显示帧数据的类

    /**
     * Data
     */
    private var mUrl: String? = null
    private var mFrameURI: String = ""
    private var mIsMute = false
    private val mScreenWidth = context.displayMetrics.widthPixels
    private val mVideoHeight = (mScreenWidth * SDKVersion.VIDEO_HEIGHT_PERCENT).toInt()


    init {
        initView()
        registerBroadcastReceiver()
    }

    private fun registerBroadcastReceiver() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initView() {
        LayoutInflater.from(mContext).inflate(R.layout.xadsdk_video_player, this)
        mVideoView = findViewById(R.id.xadsdk_player_video_textureView)
        mVideoView.setOnClickListener(this)
        mVideoView.keepScreenOn = true
        mVideoView.surfaceTextureListener = this

        layoutParams = ViewGroup.LayoutParams(mScreenWidth, mVideoHeight)

        mBtnMiniPlay = findViewById(R.id.xadsdk_small_play_btn)
        mFullBtn = findViewById(R.id.xadsdk_to_full_view)
        mLoadingBar = findViewById(R.id.loading_bar)
        mFrameView = findViewById(R.id.framing_view)
        mBtnMiniPlay.setOnClickListener(this)
        mFullBtn.setOnClickListener(this)
    }

    /**
     * Constant 播放器生命周期状态
     */
    companion object {
        private const val TIME_MSG = 0x01
        private const val TIME_INVAL = 1000
        private const val STATE_ERRO = -1
        private const val STATE_IDLE = 0
        private const val STATE_PLAYING = 1
        private const val STATE_PAUSE = 2
        private const val LOAD_TOTAL_COUNT = 3
    }





    /**
     * Status状态保护
     */
    private var mIsRealPause = false
    private var mIsRealCompelete = false
    private var mCurrentCount = 0
    private var mPlayerState = STATE_IDLE   // 默认处于空闲状态

    private var mMediaPlayer: MediaPlayer? = null
    private var mListener: ADVideoPlayerListener? = null//监听事件的回调
    private lateinit var mSreenReceiver: ScreenEventReceiver //监听屏幕是否锁屏的广播
    var mFrameLoadListener: ADFrameImageLoadListener? = null

    private val mTimerCount = object : CountDownTimer(Long.MAX_VALUE, 1000){

        override fun onFinish() {

        }

        override fun onTick(millisUntilFinished: Long) {
            mListener?.onBufferUpdate(0)
        }
    }




    override fun onPrepared(mp: MediaPlayer?) {
        mMediaPlayer = mp
        mMediaPlayer?.apply {
            this.setOnBufferingUpdateListener(this@CustomVideoView)
            mCurrentCount = 0
            mListener?.onAdVideoLoadSuccess()
            decideCanPlay()
        }
    }



    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        setCurrentState(STATE_ERRO)
        if (mCurrentCount >= LOAD_TOTAL_COUNT) {
            mListener?.onAdVideoLoadFailed()
            showPauseOrPlayerView(false)
        }

        stop()

        return true // 返回true表示我们自己处理异常，Android系统就不会处理了
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mListener?.onAdVideoLoadComplete()

        mIsRealCompelete = true
        mIsRealPause = true
        playBack()
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        mVideoSurface = Surface(surface)
        load()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.xadsdk_to_full_view -> load()
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility == View.VISIBLE && mPlayerState == STATE_PAUSE) {
            if (mIsRealPause || mIsRealCompelete) {
                pause()
            } else {
                decideCanPlay()
            }
        }
    }

    fun load(){
        if (mPlayerState != STATE_IDLE) return

        try {
            showLoadingView()
            setCurrentState(STATE_IDLE)
            checkMediaPlayer()
            mMediaPlayer!!.setDataSource(mUrl)
            mMediaPlayer!!.prepareAsync()
        } catch (e: Exception) {
            stop()
        }
    }

    fun pause() {
        if (mPlayerState != STATE_PLAYING) {
            return
        }

        setCurrentState(STATE_PAUSE)
        if (isPlaying()) {
            mMediaPlayer!!.pause()
        }
        showPauseOrPlayerView(false)
        mTimerCount.start()
    }

    fun resume() {
        if (mPlayerState != STATE_PAUSE) {
            return
        }

        if (!isPlaying()) {
            entryResumeState()  //设置播放中的状态值
            showPauseOrPlayerView(true)
            mMediaPlayer!!.start()
            mTimerCount.start()
        } else {

        }
    }

    private fun entryResumeState() {
        setCurrentState(STATE_PLAYING)
        mIsRealCompelete = false
        mIsRealPause = false
    }

    private fun isPlaying(): Boolean {
        mMediaPlayer ?: return false
        return mMediaPlayer!!.isPlaying
    }

    /**
     * 播放完成后回到初始状态
     */
    fun playBack() {
        setCurrentState(STATE_PAUSE)
        mTimerCount.cancel()
        mMediaPlayer?.run {
            this.setOnSeekCompleteListener(null)
            this.seekTo(0)
            this.pause()
        }
        showPauseOrPlayerView(false)
    }

    fun stop() {
        mMediaPlayer?.run {
            this.reset()
            this.setOnSeekCompleteListener(null)
            this.stop()
            this.release()
            mMediaPlayer = null
        }
        mTimerCount.cancel()
        setCurrentState(STATE_IDLE)

        if (mCurrentCount < LOAD_TOTAL_COUNT) {
            mCurrentCount++
            load()
        } else {
            showPauseOrPlayerView(false)
        }
    }


    fun destory() {

    }

    fun seekAndResume(position: Int) {

    }

    fun seekAndPause(position: Int) {

    }

    private fun decideCanPlay() {
        if (Utils.getVisiblePercent(mParentContainer) > SDKVersion.VIDEO_SCREEN_PERCENT) {
            setCurrentState(STATE_PAUSE)
            resume()
        } else {
            setCurrentState(STATE_PLAYING)
            pause()
        }
    }

    fun setListener(listener: ADVideoPlayerListener) {
        this.mListener = listener
    }

    private fun showLoadingView() {
        mFullBtn.visibility = View.GONE
        mLoadingBar.visibility = View.VISIBLE
        val anim = mLoadingBar.background as AnimationDrawable
        anim.start()
        mBtnMiniPlay.visibility = View.GONE
        mFrameView.visibility = View.GONE
        loadFrameImage()
    }

    /**
     * True : Pause;  False : Play
     */
    private fun showPauseOrPlayerView(show: Boolean) {
        mFullBtn.visibility = if (show) View.VISIBLE else View.GONE
        mBtnMiniPlay.visibility = if (show) View.GONE else View.VISIBLE
        mLoadingBar.clearAnimation()
        mLoadingBar.visibility = View.GONE
        if (!show) {
            mFrameView.visibility = View.VISIBLE
            loadFrameImage()
        } else {
            mFrameView.visibility = View.GONE
        }
    }


    /**
     * 异步加载定帧图
     */
    private fun loadFrameImage() {
        mFrameLoadListener?.onStartFrameLoad(mFrameURI) { loadedImage: Bitmap? ->
            if (loadedImage != null) {
                mFrameView.scaleType = ImageView.ScaleType.FIT_XY
                mFrameView.setImageBitmap(loadedImage)
            } else {
                mFrameView.scaleType = ImageView.ScaleType.FIT_CENTER
                mFrameView.setImageResource(R.drawable.xadsdk_img_error)
            }
        }
    }

    private fun setCurrentState(state: Int) {
        mPlayerState = state
    }

    private fun checkMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = createMediaPlayer()
        }
    }

    private fun createMediaPlayer(): MediaPlayer {
        val player = MediaPlayer()
        player.reset()
        player.setOnPreparedListener(this)
        player.setOnCompletionListener(this)
        player.setOnInfoListener(this)
        player.setOnErrorListener(this)
//        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player.setAudioAttributes(AudioAttributes.Builder()
            .setLegacyStreamType(AudioManager.STREAM_MUSIC).build())
        if (mVideoSurface != null && mVideoSurface!!.isValid) {
            player.setSurface(mVideoSurface)
        } else {
            stop()
        }
        return player
    }

    fun setDataSource(url: String) {
        mUrl = url
    }


    interface ADFrameImageLoadListener {
        fun onStartFrameLoad(url: String, listener: (Bitmap?) -> Unit)
    }


    /**
     * 供slot层来实现具体点击逻辑,具体逻辑还会变，
     * 如果对UI的点击没有具体监测的话可以不回调
     */
    interface ADVideoPlayerListener {

        fun onBufferUpdate(time: Int)

        fun onClickFullScreenBtn()

        fun onClickVideo()

        fun onClickBackBtn()

        fun onClickPlay()

        fun onAdVideoLoadSuccess()

        fun onAdVideoLoadFailed()

        fun onAdVideoLoadComplete()
    }

    /**
     * 监听锁屏事件的广播接收器
     */
    private inner class ScreenEventReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //主动锁屏时 pause, 主动解锁屏幕时，resume
            when (intent.action) {
                Intent.ACTION_USER_PRESENT -> if (mPlayerState == STATE_PAUSE) {
                    if (mIsRealPause) {
                        //手动点的暂停，回来后还暂停
                        pause()
                    } else {
                        decideCanPlay()
                    }
                }
                Intent.ACTION_SCREEN_OFF -> if (mPlayerState == STATE_PLAYING) {
                    pause()
                }
            }
        }
    }

}