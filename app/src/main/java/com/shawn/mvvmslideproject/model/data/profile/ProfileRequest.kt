package com.shawn.mvvmslideproject.model.data.profile

import java.io.File

data class ProfileHeadShotUploadRequest(val photo:File)
data class ProfileHeadShotUrlRequest(val url:String)
data class ProfileInfoRequest(val name:String,val gender:Int,val birth:String)
