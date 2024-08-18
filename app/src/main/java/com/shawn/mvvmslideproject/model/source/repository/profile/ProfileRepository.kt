package com.shawn.mvvmslideproject.model.source.repository.profile

import com.shawn.mvvmslideproject.model.data.profile.ProfileResponse
import com.shawn.mvvmslideproject.ui.profile.ProfileSealedStatus
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {
    fun getProfileInfo(): Flow<ProfileResponse>
    fun updateProfile(name:String,gender:Int,birth:String):Flow<ProfileSealedStatus>
    fun uploadHeadShot(photo: File):Flow<ProfileSealedStatus>
    fun updateHeadShot(url:String):Flow<ProfileSealedStatus>
}