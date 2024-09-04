package com.shawn.mvvmslideproject.extensions

import com.shawn.mvvmslideproject.MvvmSlideProjectApplication
import com.shawn.mvvmslideproject.R

fun String?.isCellEmpty(fieldName: String) =
    if (isNullOrEmpty()) {
        String.format(
            MvvmSlideProjectApplication.getStringResource(R.string.please_fill_info),
            fieldName
        )
    } else {
        this
    }


fun String?.cellTextColor(): Int =
    if (isNullOrEmpty()) {
        R.color.gray_300
    } else {
        R.color.black
    }