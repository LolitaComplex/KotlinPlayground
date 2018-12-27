package com.doing.httptest.api

import com.doing.httptest.entity.BodyClass
import okhttp3.Call
import retrofit2.http.*

interface Api {

    @GET("method/requestGet")
    fun requestGet(): Call

    @GET("static/timg.jpg")
    fun requestPic(): Call

    @FormUrlEncoded
    @POST("method/requestPostForm")
    fun requestForm(@Field("username") username: String,
                    @Field("password") password: String): Call

    @POST("method/requestPostJson")
    fun requestJson(@Body body: BodyClass): Call

    @GET("")
    fun requestMulitipartform()
}