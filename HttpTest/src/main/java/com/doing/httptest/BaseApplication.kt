package com.doing.httptest

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        BaseApplication.mContext = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }
}