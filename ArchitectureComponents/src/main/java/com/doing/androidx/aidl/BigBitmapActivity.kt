package com.doing.androidx.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.LayoutInflaterCompat
import com.doing.androidx.OnActivityShowCallback
import com.doing.androidx.R
import kotlinx.android.synthetic.main.activity_big_bitmap.*
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class BigBitmapActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BigBitmapActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, object : LayoutInflater.Factory2 {
            override fun onCreateView(
                parent: View?,
                name: String,
                context: Context,
                attrs: AttributeSet
            ): View? {
                // 可以做全局替换Xml中View指定的类
                // start
                val view = delegate.createView(parent, name, context, attrs)
                // end
                return view
            }

            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                return delegate.createView(null, name, context, attrs)
            }
        })

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_bitmap)
        val exception = RuntimeException()
        exception.stackTrace.forEach { element ->
            Log.d("Doing", "Exception className: ${element.className}")
            Log.d("Doing", "Exception fileName: ${element.fileName}")
            Log.d("Doing", "Exception methodName: ${element.methodName}")
            Log.d("Doing", "Exception lineNumber: ${element.lineNumber}")
            Log.d("Doing", "Exception isNativeMethod: ${element.isNativeMethod}")
            Log.d("Doing", "\n")

            if (element.className == this.javaClass.canonicalName) {
                val field = element.javaClass.getDeclaredField("lineNumber")
                field.isAccessible = true
                field.set(element, element.lineNumber + 100)
            }
        }
        Log.e("Doing", "My Exception", exception)

        mBtnBitmapInIntent.setOnClickListener {
            try {
                startService(Intent(this, BigBitmapActivity::class.java).apply {
                    putExtra("bundle", Bundle().apply {
                        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.big_bitmap)
                        putParcelable("bitmap", bitmap)
                    })
                })
            } catch (e: Exception) {
                Log.e(TAG, "big image intent", e)
            }
        }

        mBtnBitmapInBinder.setOnClickListener {
            try {
                startService(Intent(this, BigBitmapService::class.java).apply {
//                    putExtra("bundle", Bundle().apply {
//                        putBinder("bitmap", object : IRemoteCallback.Stub() {
//                            override fun getBitmap(): Bitmap {
//                                return BitmapFactory.decodeResource(
//                                    resources,
//                                    R.drawable.big_bitmap
//                                )
//                            }
//                        })
//                    })
                })
                bindService(Intent(this, BigBitmapService::class.java), object : ServiceConnection {

                    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                        val iRemote = IRemote.Stub.asInterface(binder)
                        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.big_bitmap)
                        iRemote.request(bitmap)
                        Log.d(TAG, "onServiceConnected: bitmap ${bitmap.byteCount}")
                    }

                    override fun onServiceDisconnected(name: ComponentName?) {
                    }

                }, BIND_AUTO_CREATE)
            } catch (e: Exception) {
                Log.e(TAG, "big image binder", e)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("Doing", "this Activity: ${this.javaClass.canonicalName}")
        }, TimeUnit.SECONDS.toMicros(20))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val binder = intent.getBundleExtra("binder")
            ?.getBinder("callback") ?: return
        val callback = OnActivityShowCallback.Stub.asInterface(binder)
        callback.callback()
    }


}