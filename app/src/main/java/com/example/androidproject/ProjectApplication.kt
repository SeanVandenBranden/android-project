package com.example.androidproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
