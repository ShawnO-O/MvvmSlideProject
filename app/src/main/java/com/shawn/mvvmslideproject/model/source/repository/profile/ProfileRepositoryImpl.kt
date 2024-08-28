package com.shawn.mvvmslideproject.model.source.repository.profile

import com.shawn.mvvmslideproject.model.room.profile.ProfileDao
import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo
import com.shawn.mvvmslideproject.model.source.local.MemberLocalDataSource
import com.shawn.mvvmslideproject.model.source.local.profile.ProfileLocalDataSource
import com.shawn.mvvmslideproject.ui.profile.ProfileSealedStatus
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@ActivityRetainedScoped
class ProfileRepositoryImpl @Inject constructor(
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val memberLocalDataSource: MemberLocalDataSource,
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun hasMemberId() = memberLocalDataSource.hasMemberId()

    override suspend fun getProfileInfo(): Flow<ProfileInfo> {
        return flow {
            var profileInfo: ProfileInfo?
            withContext(Dispatchers.IO) {
                profileInfo = profileDao.getProfile("${memberLocalDataSource.getMemberId()}")

                if (profileInfo == null) {
                    profileDao.insertProfile(ProfileInfo(memberId = memberLocalDataSource.getMemberId()))
                    profileInfo = profileDao.getProfile("${memberLocalDataSource.getMemberId()}")
                }
            }
            emit(profileInfo!!)
        }
    }

    override suspend fun updateProfile(
        name: String,
        gender: Int,
        birth: String
    ): Flow<ProfileSealedStatus> {
        return flow {

        }
    }

    override suspend fun uploadHeadShot(photo: File): Flow<ProfileSealedStatus> {
        return flow {

        }
    }

    override suspend fun updateHeadShot(url: String): Flow<ProfileSealedStatus> {
        return flow {

        }
    }

    override suspend fun updateProfileName(name: String): Flow<ProfileSealedStatus> {
        return flow {
            if (profileLocalDataSource.isProfileNameCorrect(name)) {
                withContext(Dispatchers.IO) {
                    profileDao.updateProfileName(name, "${memberLocalDataSource.getMemberId()}")
                }
                emit(ProfileSealedStatus.Success)
            }
        }
    }

    override suspend fun updateProfileGender(gender: String): Flow<ProfileSealedStatus> {
        return flow {
            if (!profileLocalDataSource.isProfileGenderCorrect(gender)) {
                withContext(Dispatchers.IO) {
                    profileDao.updateGender(gender, "${memberLocalDataSource.getMemberId()}")
                }
                emit(ProfileSealedStatus.Success)
            } else {
                emit(ProfileSealedStatus.ShouldNotBeEmpty("性別不可為空"))
            }
        }
    }

    override suspend fun updateProfileBirth(birth: String): Flow<ProfileSealedStatus> {
        return flow {
            if (profileLocalDataSource.isProfileBirthCorrect(birth)) {
                emit(ProfileSealedStatus.Success)
            } else {
                emit(ProfileSealedStatus.ShouldNotBeEmpty("生日不可為空"))
            }
        }
    }

    override suspend fun updateProfileEmail(email: String): Flow<ProfileSealedStatus> {
        return flow {
            if (profileLocalDataSource.isEmailCorrect(email)) {
                withContext(Dispatchers.IO) {
                    profileDao.updateProfileEmail(email, "${memberLocalDataSource.getMemberId()}")
                }
                emit(ProfileSealedStatus.Success)
            } else {
                emit(ProfileSealedStatus.FormatError("格式錯誤"))
            }
        }
    }
}