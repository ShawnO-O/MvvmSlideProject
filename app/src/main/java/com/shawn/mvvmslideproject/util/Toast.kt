package com.shawn.mvvmslideproject.util

import android.content.Context
import android.widget.Toast

fun ShowToastShort(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

}

fun ShowToastLong(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

}

fun showToastCustom(){
    //feature
}