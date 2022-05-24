package com.munyao.safeboda

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SafeBodaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}