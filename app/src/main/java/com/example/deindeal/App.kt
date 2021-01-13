package com.example.deindeal

import android.app.Application
import android.content.Context
import com.example.deindeal.network.NetworkApi
import timber.log.Timber

class App : Application() {


    companion object {
        lateinit var appContext: Context

        val networkConnection by lazy {
            NetworkApi.contentApi()
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appContext = applicationContext
    }
}