package com.doing.androidx

import android.app.Application
import android.os.Looper
import android.os.StrictMode
import android.util.Printer
import androidx.lifecycle.LiveData

class AppApplication : Application() {

    companion object {
        private lateinit var instance: Application

        fun getInstance(): Application {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Looper.getMainLooper().setMessageLogging { log ->
            // logging.println(">>>>> Dispatching to " + msg.target + " " + msg.callback + ": " + msg.what);
            // logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
            log.startsWith(">>>>> Dispatching") // start
            // 计算耗时
            log.startsWith("<<<<< Finished") // finish
        }

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .detectCustomSlowCalls() // 自定义耗时操作
            .detectDiskReads()
            .detectDiskWrites()
            .detectNetwork()
            .penaltyLog()
            .build())

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectActivityLeaks()
            .detectLeakedClosableObjects()
            .setClassInstanceLimit(LiveData::class.java, 1)
            .penaltyLog()
            .build())
    }
}