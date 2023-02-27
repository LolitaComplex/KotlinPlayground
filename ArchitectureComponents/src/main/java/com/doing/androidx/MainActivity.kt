package com.doing.androidx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.system.Os
import android.util.Log
import android.view.Choreographer
import android.widget.Button
import com.doing.androidx.aidl.BigBitmapActivity
import com.doing.androidx.aspectj.MethodTrace
import com.doing.androidx.mvp.MvpActivity
import com.doing.androidx.mvvm.MvvmDataBindingActivity
import com.doing.androidx.mvvm.MvvmLiveDataActivity
import com.doing.androidx.postview.PostViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Time
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), Runnable {

    private val buffer = mutableListOf<ByteArray>()
    val handler = Handler(Looper.getMainLooper())


    @MethodTrace
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnMvp.setOnClickListener {
            startActivity(Intent(this, MvpActivity::class.java))
        }

        mBtnMvvmDatabinding.setOnClickListener {
            startActivity(Intent(this, MvvmDataBindingActivity::class.java))
        }

        mBtnMvvmLiveData.setOnClickListener {
            startActivity(Intent(this, MvvmLiveDataActivity::class.java))
        }

        mBtnMvvmPostView.setOnClickListener {
            startActivity(Intent(this, PostViewActivity::class.java))
        }

        mBtnBigBitmap.setOnClickListener {
            startActivity(Intent(this, BigBitmapActivity::class.java))
        }

//        NetworkUtils.getNetStats(this)


        showMemoryInfo()
        handler.postDelayed(this, 5 * 1000)
    }

    private fun showMemoryInfo() {
        val runtime = Runtime.getRuntime()
        val maxMemory = runtime.maxMemory()
        TimeUnit.SECONDS
        val totalMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        Log.d("Doing", "Max: ${MemorySize.BYTE.toMB(maxMemory)} \t" +
                " Total: ${MemorySize.BYTE.toMB(totalMemory)} \t" +
                " Free: ${MemorySize.BYTE.toMB(freeMemory)}")
    }

    override fun run() {
        buffer.add(ByteArray(1024 * 1024 * 50))
        showMemoryInfo();
        handler.postDelayed(this, TimeUnit.SECONDS.toMillis(10))
    }

    private enum class MemorySize {
        BYTE {
            override fun toKB(long: Long): String {
                return "${(long / 1024.0).toInt()}kb"
            }

            override fun toMB(long: Long): String {
                return "${(long / 1024.0 / 1024.0).toInt()}mb"
            }
        };

        abstract fun toKB(long: Long): String
        abstract fun toMB(long: Long): String

    }

    override fun onLowMemory() {
        super.onLowMemory()
        buffer.clear()
    }

}