package com.doing.httptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.doing.httptest.api.Api
import com.doing.httptest.entity.BodyClass
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import kotlinx.android.synthetic.main.activity_retrofit.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitActivity : AppCompatActivity() {

    companion object {
        private const val HOST = "http://www.doing.com:8000/"
        private const val TAG = "RetrofitActivity"
    }

    private val mRetrofit by lazy {
        val interceptor = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("DoingRequest")
            .response("DoingResponse")
            .build()

        Retrofit.Builder()
            .baseUrl(HOST)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        val api = mRetrofit.create(Api::class.java)

        mBtnGet.setOnClickListener {
            api.requestGet().enqueue(object: Callback{

                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 RequestGet", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        mBtnGetPic.setOnClickListener {
            api.requestPic().enqueue(object: Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 RequestPic", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        mBtnPostForm.setOnClickListener {
            api.requestForm("布鲁马", "123456").enqueue(object: Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 RequestForm", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        mBtnPostJson.setOnClickListener {
            api.requestJson(BodyClass("布鲁马", "123456")).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 Json", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

    }
}