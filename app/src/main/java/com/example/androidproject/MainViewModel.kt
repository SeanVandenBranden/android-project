package com.example.androidproject

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

class MainViewModel : ViewModel() {
    var appJustLaunched by mutableStateOf(true)
        private set

    private val tag = "MainViewModel"
    private lateinit var context: WeakReference<Context>

    fun setContext(activityContext: Context) {
        context = WeakReference(activityContext)
    }
}
