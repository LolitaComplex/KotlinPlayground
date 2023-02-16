package com.doing.androidx.postview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.doing.androidx.R

class PostViewActivity : AppCompatActivity() {

    companion object {
        const val TAG = "PostViewActivity"
        val start by lazy { System.currentTimeMillis() }
    }


    private val threadView by lazy {
        FrameLayout(this).apply {
            layoutParams = LayoutParams(600, 1500)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_view)

        val ivContent = findViewById<ImageView>(R.id.mIvContent)
        val flContainer = findViewById<FrameLayout>(R.id.mFlContainer)
        findViewById<Button>(R.id.mBtnStartThread).setOnClickListener {
            Thread {
                var time = System.currentTimeMillis() - start
                Log.d(TAG, "Thread start: $time")
                createRecyclerView()
                val widthSpec = View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.EXACTLY)
                val heightSpec = View.MeasureSpec.makeMeasureSpec(1500, View.MeasureSpec.EXACTLY)
                threadView.measure(widthSpec, heightSpec)
                threadView.layout(0, 0, threadView.measuredWidth, threadView.measuredHeight)
                val bitmap = Bitmap.createBitmap(600, 1500, Bitmap.Config.ARGB_8888)
                threadView.draw(Canvas(bitmap))
                time = System.currentTimeMillis() - start
                Log.d(TAG, "Thread end: $time")
                Handler(Looper.getMainLooper()).post {
                    ivContent.setImageBitmap(bitmap)
                    time = System.currentTimeMillis() - start
                    Log.d(TAG, "Thread main: $time")
                }
            }.start()
        }

        findViewById<Button>(R.id.mBtnAddView).setOnClickListener {
            ivContent.visibility = View.GONE
            flContainer.addView(threadView)
        }

        val activityViewPager = findViewById<ViewPager>(R.id.mActivityViewPager)
        activityViewPager.adapter = PostViewPagerAdapter.ViewPagerAdapter()
    }

    private fun createRecyclerView() {
        MyLayoutInflater(this).inflate(R.layout.layout_async_recycler,
            threadView, true)
        val recyclerView = threadView.findViewById<RecyclerView>(R.id.mRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val textView = TextView(this@PostViewActivity).apply {
                    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 100)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f)
                    setTextColor(Color.BLACK)
                    gravity = Gravity.CENTER
                }
                return object : RecyclerView.ViewHolder(textView){};
            }

            @SuppressLint("SetTextI18n")
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as TextView).text = "Holder Item: $position"
            }

            override fun getItemCount(): Int {
                return 100
            }

        }
    }

    private fun createViewPager() {
        MyLayoutInflater(this).inflate(R.layout.layout_async_view_pager,
            threadView, true)
        val viewPager = threadView.findViewById<PostViewPager>(R.id.mViewPager)
        viewPager.adapter = PostViewPagerAdapter.ViewPagerAdapter()
        viewPager.setBackgroundColor(Color.YELLOW)
        viewPager.onPubAttachedToWindow()
    }

    private class MyLayoutInflater(context: Context) : LayoutInflater(context){

        private val sClassPrefixList = arrayOf(
            "android.widget.",
            "android.webkit.",
            "android.app."
        )

        override fun cloneInContext(newContext: Context?): LayoutInflater {
            return MyLayoutInflater(newContext!!)
        }


        @Throws(ClassNotFoundException::class)
        override fun onCreateView(name: String?, attrs: AttributeSet?): View? {
            for (prefix in sClassPrefixList) {
                try {
                    val view = createView(name, prefix, attrs)
                    if (view != null) {
                        return view
                    }
                } catch (e: ClassNotFoundException) {
                    // In this case we want to let the base class take a crack
                    // at it.
                }
            }
            return super.onCreateView(name, attrs)
        }
    }



}