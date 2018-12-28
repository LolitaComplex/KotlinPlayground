package com.doing.httptest.api

import com.doing.httptest.entity.BodyClass
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("method/requestGet")
    fun requestGet(): Call<ResponseBody>

    @GET("static/timg.jpg")
    fun requestPic(): Call<ResponseBody>

    @FormUrlEncoded
    @POST("method/requestPostForm")
    fun requestForm(@Field("username") username: String,
                    @Field("password") password: String): Call<ResponseBody>

    @POST("method/requestPostJson")
    fun requestJson(@Body body: BodyClass): Call<ResponseBody>

    @POST("method/requestPostText")
    fun requestText(@Body body: RequestBody): Call<ResponseBody>

    @Multipart
    @POST("method/requestPostMultipart")
    fun requestMultipartForm(@Part("file\"; filename=\"Archer.jpg") body: RequestBody): Call<ResponseBody>
}