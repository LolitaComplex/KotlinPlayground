package com.doing.httptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.doing.httptest.api.Api
import com.doing.httptest.entity.BodyClass
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import kotlinx.android.synthetic.main.activity_retrofit.*
import okhttp3.*
import okhttp3.internal.platform.Platform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
            .addConverterFactory(ScalarsConverterFactory.create())
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
            api.requestGet().enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "网络错误 GET", t)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}

            })
        }

        mBtnGetPic.setOnClickListener {
            api.requestPic().enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "网络错误 RequestPic", t)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
            })
        }

        mBtnPostForm.setOnClickListener {
            api.requestForm("布鲁马", "123456").enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, e: Throwable) {
                    Log.e(TAG, "网络错误 RequestForm", e)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
            })
        }

        mBtnPostJson.setOnClickListener {
            api.requestJson(BodyClass("布鲁马", "123456")).enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, e: Throwable) {
                    Log.e(TAG, "网络错误 Json", e)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
            })
        }

        mBtnPostText.setOnClickListener {
            api.requestText(RequestBody.create(MediaType.parse("text/plain"), "纯文本提交"))
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e(TAG, "网络错误 Post Text", t)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}
                })
        }

        mBtnPostMultipart.setOnClickListener {
            api.requestMultipartForm(
                FormBody.Builder()
                    .add("username", "布鲁马")
                    .add("password", "123456789")
                    .build()).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "网络错误 Multipart", t)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {}

            })
        }
    }
}