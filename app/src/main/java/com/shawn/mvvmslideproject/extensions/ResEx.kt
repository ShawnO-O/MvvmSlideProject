package com.shawn.mvvmslideproject.extensions

import com.shawn.mvvmslideproject.MvvmSlideProjectApplication.Companion.applicationContext

    fun Int.getStringResource(): String {
        return applicationContext().getString(this)
    }

    fun Int.getColorResource(): Int {
        return applicationContext().getColor(this)

    }