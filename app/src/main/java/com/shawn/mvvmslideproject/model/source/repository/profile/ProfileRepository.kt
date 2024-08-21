package com.shawn.mvvmslideproject.model.source.repository.profile

import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo
import com.shawn.mvvmslideproject.ui.profile.ProfileSealedStatus
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {
 suspend fun hasMemberId(): Boolean
 suspend fun getProfileInfo(): Flow<ProfileInfo>
 suspend fun updateProfile(name: String, gender: Int, birth: String): Flow<ProfileSealedStatus>
 suspend fun uploadHeadShot(photo: File): Flow<ProfileSealedStatus>
 suspend fun updateHeadShot(url: String): Flow<ProfileSealedStatus>
 suspend fun updateProfileName(name: String): Flow<ProfileSealedStatus>
 suspend fun updateProfileGender(gender: String): Flow<ProfileSealedStatus>
 suspend fun updateProfileBirth(birth: String): Flow<ProfileSealedStatus>
 suspend fun updateProfileEmail(email: String): Flow<ProfileSealedStatus>
}