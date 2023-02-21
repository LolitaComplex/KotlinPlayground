package com.doing.androidx.aidl

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import android.util.Log

class BigBitmapService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(BigBitmapActivity.TAG, "BigBitmapService: onCreate")
    }

    private val binder by lazy {
        object : IRemote.Stub() {
            override fun request(bitmap: Bitmap) {
                Log.d(BigBitmapActivity.TAG, "bitmap service: ${bitmap.byteCount}")
            }
        }
    }
}