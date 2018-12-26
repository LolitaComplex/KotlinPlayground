package com.doing.httptest.manager

import android.util.Log
import com.doing.httptest.BaseApplication
import com.doing.httptest.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.platform.Platform
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class HttpDownloadManager private constructor() {

    companion object {
        val INSTANCE by lazy { HttpDownloadManager() }
        private const val TAG = "HttpDownloadManager"
    }

    private val mOkHttpClient: OkHttpClient
    private val mExternalDir by lazy {
        val file = BaseApplication.mContext.getExternalFilesDir("download")
        if (file != null && file.exists()) {
            file.mkdirs()
        }
        file
    }

    private val mDownInfo = HashMap<String, DownloadInfo>()

    init {
        val interceptor = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("DoingRequest")
            .response("DoingResponse")
            .build()

        mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }


    fun download(url: String, callback: (info: DownloadInfo) -> Unit) {
        val subscribe = Flowable.just(url)
            .flatMap {
                Log.w(TAG, "CreateDownloadInfo CurrentThread ${Thread.currentThread().name}")
                Flowable.just(createDownloadInfo(it))
            }
            .map {
                Log.w(TAG, "CreateFile CurrentThread ${Thread.currentThread().name}")
                createLocalFile(it)
            }
            .flatMap { info ->
                Log.w(TAG, "CreateRx CurrentThread ${Thread.currentThread().name}")
                Flowable.create(DownloadOnSubscribe(info), BackpressureStrategy.DROP)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                Log.w(TAG, "Finally CurrentThread ${Thread.currentThread().name} + Data：${it.mDownloadProgress}")
            }
    }

    inner class DownloadOnSubscribe internal constructor(val info: DownloadInfo) : FlowableOnSubscribe<DownloadInfo> {
        override fun subscribe(emitter: FlowableEmitter<DownloadInfo>) {
            val request = Request.Builder().url(info.mUrl)
                .addHeader("Range", "bytes=0-${info.mFileLength}")
                .build()
            var bufferOut: BufferedOutputStream? = null
            try {
                val response = mOkHttpClient.newCall(request).execute()
                val inputStream = response.body()?.byteStream()

                emitter.onNext(info)
                bufferOut = BufferedOutputStream(FileOutputStream(info.mDownloadFile))


                inputStream?.apply {
                    val byteBuf = ByteArray(1024 * 1024)
                    var len = 0; var subLen = 0

                    this.use {
                        while (true) {
                            len = it.read(byteBuf)
                        }
                    }

//                    while (read(byteBuf).apply { len = this } != -1) {
//                        bufferOut.write(byteBuf, 0, len)
//                        subLen += len
//                        info.mDownloadProgress = subLen / info.mFileLength.toFloat()
//                        emitter.onNext(info)
//                        bufferOut.flush()
//                    }
                    emitter.onComplete()
                }

            } catch (e: Exception) {
                emitter.onError(e)
                Log.e(TAG, "下载异常", e)
            } finally {
                IOUtils.close(bufferOut)
            }
        }
    }

    private fun createDownloadInfo(url: String): DownloadInfo {
        val downInfo = DownloadInfo()
        downInfo.mFileLength = getContentLength(url)
        downInfo.mFileName = url.substring(url.lastIndexOf("/"))
        downInfo.mUrl = url
        return downInfo
    }

    private fun getContentLength(url: String): Long {
        val request = Request.Builder().url(url).head().build()
        val response = mOkHttpClient.newCall(request).execute()
        return response.header("Content-Length")?.toLong() ?: 0L
    }

    private fun createLocalFile(info: DownloadInfo): DownloadInfo {
        val file = File(mExternalDir, info.mFileName)
//        if (!file.exists()) {
//            file.createNewFile()
//        }
        info.mDownloadFile = file
        return info
    }

}


