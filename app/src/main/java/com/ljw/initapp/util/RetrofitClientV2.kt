package com.ljw.initapp.util

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.Call
import okhttp3.ResponseBody

object RetrofitClientV2 {
    private var instance: retrofit2.Retrofit? = null
    fun getInstance(): retrofit2.Retrofit? {
        if (instance == null) {
            instance = retrofit2.Retrofit.Builder()
                //TODO gradle 에서 URL 설정
//                .baseUrl(BuildConfig.URL_BASIC + "/")
                .client(
                    OkHttpClient()
                        .newBuilder()
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return instance
    }
}

interface RetrofitServiceV2 {
    //로그인 인증번호 요청
    @GET(ServerAPi.API_GET_LOGIN_AUTH_NUMBER)
    fun requestAuthNumForLogin(
        @Query("tell_no") tel: String,
        @Query("type") type: String
    ): Call<ResponseBody>
}
