package com.doing.httptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.internal.platform.Platform
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        private const val HOST = "http://www.doing.com:8000/"
        private const val TAG = "MainActivity"
    }

    private val mOkHttpClient: OkHttpClient by lazy {

//        val httpInterceptor = HttpLoggingInterceptor {
//            try {
//                val text = URLDecoder.decode(it, "utf-8")
//                Log.e("HttpLogging", text)
//            } catch (e: Exception) {
//                Log.e("HttpLogging", it)
//            }
//        }
//
//        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .build()

        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
//            .addInterceptor(httpInterceptor)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnGet.setOnClickListener {
            val request = Request.Builder().url(HOST + "method/requestGet").get().build()
            val call = mOkHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误", e)
                }

                override fun onResponse(call: Call, response: Response) {
//                    Log.d(TAG, response.body()?.string())
                }
            })
        }

        mBtnPostForm.setOnClickListener {
        }
    }


}
