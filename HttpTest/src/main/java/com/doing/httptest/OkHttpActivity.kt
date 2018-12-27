package com.doing.httptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.doing.httptest.manager.HttpDownloadManager
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import kotlinx.android.synthetic.main.activity_ok_http.*
import okhttp3.*
import okhttp3.internal.platform.Platform
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class OkHttpActivity : AppCompatActivity() {

    companion object {
        private const val HOST = "http://www.doing.com:8000/"
        private const val TAG = "OkHttpActivity"
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
            .request("DoingRequest")
            .response("DoingResponse")
            .build()

        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
//            .addInterceptor(httpInterceptor)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http)

        // GET
        mBtnGet.setOnClickListener {
            val request = Request.Builder()
                .url(HOST + "method/requestGet")
                .get().build()
            val call = mOkHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        // GET Picture
        mBtnGetPic.setOnClickListener {
            val request = Request.Builder().url(HOST + "static/timg.jpg").get().build()
            mOkHttpClient.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 getPic", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        // Get Download
        mBtnDownload.setOnClickListener {
            HttpDownloadManager.INSTANCE.download(HOST + "media/test.mp4") { info ->
                Log.d(TAG, "${info.mDownloadProgress}")
            }
        }

        // Post Form
        mBtnPostForm.setOnClickListener {
            val request = Request.Builder().url(HOST + "method/requestPostForm")
                .post(
                    FormBody.Builder()
                        .add("username", "布鲁马")
                        .add("password", "123456789")
                        .build()
                ).build()
            mOkHttpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {}

                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 Doing", e)
                }

            })
        }

        // Post Json
        mBtnPostJson.setOnClickListener {
            val jsonObj = JSONObject()
            jsonObj.put("Type", "Json")
            jsonObj.put("Content", "Http熟悉中")

            val request = Request.Builder().url(HOST + "method/requestPostJson")
                .post(RequestBody.create(MediaType.parse("application/json"), jsonObj.toString()))
                .build()

            mOkHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 JsonDoing", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        // Post Text
        mBtnPostText.setOnClickListener {
            val request = Request.Builder().url(HOST + "method/requestPostText")
                .post(RequestBody.create(MediaType.parse("text/plain"), "这是我一次传输的文字"))
                .build()

            mOkHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 PostText", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        // Post Multipart/from-data
        mBtnPostMultipart.setOnClickListener {
            val jsonObj = JSONObject()
            jsonObj.put("Type", "Json")
            jsonObj.put("Content", "Http熟悉中")

            val request = Request.Builder().url(HOST + "method/requestPostMultipart")
                .post(
                    MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("Test", "测试文本")
                        .addFormDataPart("file", "Archer.jpg",
                            FormBody.Builder()
                            .add("username", "布鲁马")
                            .add("password", "123456789")
                            .build())
                        .addPart(
                            FormBody.Builder()
                                .add("username", "布鲁马")
                                .add("password", "123456789")
                                .build())
                        .addPart(
                            Headers.of("a", "b", "c", "e"),
                            RequestBody.create(MediaType.parse("application/json"), jsonObj.toString())
                        )
                        .build()
                )
                .build()

            mOkHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "网络错误 Multipart", e)
                }

                override fun onResponse(call: Call, response: Response) {}
            })
        }
    }


}
