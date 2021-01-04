package com.example.deindeal

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appContext = applicationContext
    }
}