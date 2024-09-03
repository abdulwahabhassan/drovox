package com.drovox.app.ui

import android.app.Application
import com.drovox.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DrovoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimberLog()
    }

    private fun initTimberLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}