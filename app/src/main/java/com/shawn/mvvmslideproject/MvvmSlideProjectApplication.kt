package com.shawn.mvvmslideproject

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MvvmSlideProjectApplication:Application() {
    companion object {
        private var instance: MvvmSlideProjectApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}