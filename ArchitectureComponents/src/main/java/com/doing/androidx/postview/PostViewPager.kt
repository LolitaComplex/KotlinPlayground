package com.doing.androidx.postview

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class PostViewPager @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): ViewPager(context, attrs) {

    fun onPubAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun getWindowToken(): IBinder {
        return (context as Activity).window.decorView.windowToken
    }
}