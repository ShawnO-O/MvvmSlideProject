package com.shawn.mvvmslideproject.util.extensions

import com.shawn.mvvmslideproject.R

fun String?.isCellEmpty(fieldName: String) =
    if (isNullOrEmpty()) {
        "請填寫$fieldName"
    } else {
        this
    }


fun String?.cellTextColor(): Int =
    if (isNullOrEmpty()) {
        R.color.gray_300
    } else {
        R.color.black
    }