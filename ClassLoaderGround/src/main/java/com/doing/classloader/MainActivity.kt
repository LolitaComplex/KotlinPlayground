package com.doing.classloader

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val systemClassLoader = ClassLoader.getSystemClassLoader()
        Log.d("DoingClassLoader", systemClassLoader.toString()) //  dalvik.system.PathClassLoader

        val bootClassLoader = systemClassLoader.parent
        Log.d("DoingClassLoader", bootClassLoader.toString()) // java.lang.BootClassLoader@4ed5013

        val bootStrapClassLoader = bootClassLoader.parent // null
        Log.d("DoingClassLoader", bootStrapClassLoader?.toString() ?: "null")

        val androidClassLoader = MainActivity::class.java.classLoader
        Log.w("DoingClassLoader", androidClassLoader.toString()) // dalvik.system.PathClassLoader

        val javaClassLoader = String::class.java.classLoader
        Log.w("DoingClassLoader", javaClassLoader.toString()) // java.lang.BootClassLoader@4ed5013

        Log.i("DoingClassLoader", "=================================")

    }

}