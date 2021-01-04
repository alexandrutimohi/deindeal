package com.example.deindeal.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkApi {
    companion object {

        private const val CONNECT_TIMEOUT = 20L
        private const val READ_TIMEOUT = 20L

        private val okHttpClient: OkHttpClient = OkHttpClient
            .Builder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()


        private fun networkConnection(url: String): INetworkConnection {
            return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(okHttpClient)
                .build()
                .create(INetworkConnection::class.java)
        }

        fun contentApi(): INetworkConnection {
            return networkConnection("https://cpalasanu.github.io/")
        }


    }
}