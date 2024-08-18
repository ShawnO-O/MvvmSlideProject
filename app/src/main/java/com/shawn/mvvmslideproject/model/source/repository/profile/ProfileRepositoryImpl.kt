package com.shawn.mvvmslideproject.model.source.repository.profile

import com.shawn.mvvmslideproject.model.data.profile.ProfileResponse
import com.shawn.mvvmslideproject.ui.profile.ProfileSealedStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class ProfileRepositoryImpl:ProfileRepository {
    override fun getProfileInfo(): Flow<ProfileResponse> {
        return flow{

        }
    }

    override fun updateProfile(
        name: String,
        gender: Int,
        birth: String
    ): Flow<ProfileSealedStatus> {
        return flow{

        }
    }

    override fun uploadHeadShot(photo: File): Flow<ProfileSealedStatus> {
        return flow{

        }
    }

    override fun updateHeadShot(url: String): Flow<ProfileSealedStatus> {
        return flow{

        }
    }
}