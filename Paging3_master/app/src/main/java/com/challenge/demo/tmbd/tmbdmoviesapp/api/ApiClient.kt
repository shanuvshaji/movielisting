package com.challenge.demo.tmbd.tmbdmoviesapp.api

import android.os.Build
import android.util.Log
import com.challenge.demo.tmbd.tmbdmoviesapp.BuildConfig
import com.challenge.demo.tmbd.tmbdmoviesapp.BuildConfig.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {


    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
//        private const val API_KEY = "5cc0ce3b36da0a3731ace4d2ab767c40"
        val interceptor = Interceptor { chain ->
            val newUrl =
                chain.request().url.newBuilder()
                    .addQueryParameter("api_key",API_KEY )
                    .build()
            val request = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(request)
        }

        /**
         *  Creating Http Client Service with interceptors
         */

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e("log-->", message)
                }
            })
            logger.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .addInterceptor(interceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}