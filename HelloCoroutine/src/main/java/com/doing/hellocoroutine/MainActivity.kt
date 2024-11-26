package com.doing.hellocoroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("Doing", Thread.currentThread().name)

        thread {
            Log.d("Doing", Thread.currentThread().name)

        }

        val executors = Executors.newCachedThreadPool()
        executors.execute {
            Log.d("Doing", Thread.currentThread().name)

        }

        val singleThreadContext = newSingleThreadContext("ThreadSingle")
        val fixedThreadContext = newFixedThreadPoolContext(4, "FixedThread")
        val coroutineScope = CoroutineScope(EmptyCoroutineContext)
        coroutineScope.launch {
            Log.d("Doing", Thread.currentThread().name)
        }

        Dispatchers.Default // 跟核心数相同
        Dispatchers.IO // 64线程
        Dispatchers.Main // 主线程
        Dispatchers.Unconfined // 使用当前线程执行
    }
}