package com.shawn.mvvmslideproject

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MvvmSlideProjectApplication:Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MvvmSlideProjectApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getStringResource(id:Int):String{
            return applicationContext().getString(id)
        }

        fun getColorResource(id:Int):Int{
            return applicationContext().getColor(id)

        }
    }
}