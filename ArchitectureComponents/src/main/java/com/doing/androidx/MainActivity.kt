package com.doing.androidx

import android.graphics.SurfaceTexture
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val surfaceView: SurfaceView = SurfaceView(this)
//        surfaceView.holder.addCallback(object: SurfaceHolder.Callback {
//            override fun surfaceCreated(holder: SurfaceHolder?) {
//
//            }
//
//            override fun surfaceChanged(
//                holder: SurfaceHolder?,
//                format: Int,
//                width: Int,
//                height: Int
//            ) {
//            }
//
//            override fun surfaceDestroyed(holder: SurfaceHolder?) {
//            }
//
//        })
//
//        val textureView = TextureView(this)
//        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
//            override fun onSurfaceTextureAvailable(
//                surface: SurfaceTexture?,
//                width: Int,
//                height: Int
//            ) {
//            }
//
//            override fun onSurfaceTextureSizeChanged(
//                surface: SurfaceTexture?,
//                width: Int,
//                height: Int
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
//                TODO("Not yet implemented")
//            }
//
//        }
//
//        val obj = Object()
//        val clazz = Class.forName("android.app.ActivityManager")
//        Proxy.newProxyInstance(Thread.currentThread().contextClassLoader,
//            arrayOf(clazz), object : InvocationHandler {
//
//                override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
//                    return method.invoke(obj, args)
//                }
//            })
    }
}
