package com.example.rxsample.request

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

class ServiceGenerator {

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"

        private val okHttpClient = OkHttpClient.Builder().addInterceptor(getInterceptor()).build()

        private val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        private val retrofit = retrofitBuilder.build()

        private val requestApi = retrofit.create(RequestApi::class.java)

        fun getRequestApi() = requestApi

        private fun getInterceptor() : HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return interceptor
        }
    }
}