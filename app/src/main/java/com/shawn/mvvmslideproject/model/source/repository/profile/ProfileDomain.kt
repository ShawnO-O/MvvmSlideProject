package com.shawn.mvvmslideproject.model.source.repository.profile

import java.io.File

interface ProfileDomain {
    fun updateProfile(name:String,gender:Int,birth:String)
    fun uploadHeadShot(photo: File)
    fun updateHeadShot(url:String)
}