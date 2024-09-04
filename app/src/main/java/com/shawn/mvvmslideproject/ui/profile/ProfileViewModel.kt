package com.shawn.mvvmslideproject.ui.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.MvvmSlideProjectApplication
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepositoryImpl
import com.shawn.mvvmslideproject.model.source.repository.profile.ProfileRepositoryImpl
import com.shawn.mvvmslideproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ResumeFieldType {
    NAME, GENDER, BIRTH, EMAIL
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepositoryImpl: ProfileRepositoryImpl,
    private val loginRepositoryImpl: LoginRepositoryImpl,
) : BaseViewModel() {
    private val _hasMemberId = MutableStateFlow(false)
    val hasMemberId: StateFlow<Boolean> = _hasMemberId
    private val _profileInfo = MutableStateFlow(ProfileInfo())
    val profileInfo: StateFlow<ProfileInfo> = _profileInfo
    fun hasMemberId() {
        viewModelScope.launch {
            _hasMemberId.value = profileRepositoryImpl.hasMemberId()
        }
    }

    fun getProfileData() {
        viewModelScope.launch {
            profileRepositoryImpl.getProfileInfo().collect {
                _profileInfo.value = it
            }
        }
    }

    fun saveHeadshot(uri:Uri){
        val profile = _profileInfo.value
        profile.headerShot = uri.toString()
        _profileInfo.value = profile
    }

    fun saveName(name: String) {
        viewModelScope.launch {
            profileRepositoryImpl.updateProfileName(name).collect {
                when (it) {
                    is ProfileSealedStatus.Success -> {
                        _toastShardFlow.emit("儲存成功")
                    }

                    is ProfileSealedStatus.Failed -> {
                        //TODO not yet implemented
                    }

                    is ProfileSealedStatus.ShouldNotBeEmpty -> {
                        _toastShardFlow.emit(it.type.showProfileEditErrorToast())
                    }

                    is ProfileSealedStatus.FormatError -> {
                        _toastShardFlow.emit(showProfileEditFormatError())
                    }
                }
            }
        }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch {
            profileRepositoryImpl.updateProfileEmail(email).collect {
                when (it) {
                    is ProfileSealedStatus.Success -> {
                        _toastShardFlow.emit("儲存成功")
                    }

                    is ProfileSealedStatus.Failed -> {
                        //TODO not yet implemented
                    }

                    is ProfileSealedStatus.ShouldNotBeEmpty -> {
                        _toastShardFlow.emit(it.type.showProfileEditErrorToast())
                    }

                    is ProfileSealedStatus.FormatError -> {
                        _toastShardFlow.emit(showProfileEditFormatError())
                    }
                }
            }
        }
    }

    fun saveGender(gender: String) {
        viewModelScope.launch {
            profileRepositoryImpl.updateProfileGender(gender).collect {
                when (it) {
                    is ProfileSealedStatus.Success -> {
                        _toastShardFlow.emit("儲存成功")
                    }

                    is ProfileSealedStatus.Failed -> {
                        //TODO not yet implemented
                    }

                    is ProfileSealedStatus.ShouldNotBeEmpty -> {
                        _toastShardFlow.emit(it.type.showProfileEditErrorToast())
                    }

                    is ProfileSealedStatus.FormatError -> {
                        _toastShardFlow.emit(showProfileEditFormatError())
                    }
                }
            }
        }
    }

    fun saveBirth(birth: String) {
        viewModelScope.launch {
            profileRepositoryImpl.updateProfileBirth(birth).collect {
                when (it) {
                    is ProfileSealedStatus.Success -> {
                        _toastShardFlow.emit("儲存成功")
                    }

                    is ProfileSealedStatus.Failed -> {
                        //TODO not yet implemented
                    }

                    is ProfileSealedStatus.ShouldNotBeEmpty -> {
                        _toastShardFlow.emit(it.type.showProfileEditErrorToast())
                    }

                    is ProfileSealedStatus.FormatError -> {
                        _toastShardFlow.emit(showProfileEditFormatError())
                    }
                }
            }
        }
    }

    fun showProfileEditFormatError() =
        MvvmSlideProjectApplication.getStringResource(R.string.format_error)

    fun ResumeFieldType.showProfileEditErrorToast() =
        String.format(
            MvvmSlideProjectApplication.getStringResource(R.string.should_not_empty),
            when (this) {
                ResumeFieldType.NAME -> MvvmSlideProjectApplication.getStringResource(
                    R.string.profile_name
                )

                ResumeFieldType.GENDER -> MvvmSlideProjectApplication.getStringResource(
                    R.string.profile_gender
                )

                ResumeFieldType.BIRTH -> MvvmSlideProjectApplication.getStringResource(
                    R.string.profile_birth
                )

                ResumeFieldType.EMAIL -> MvvmSlideProjectApplication.getStringResource(
                    R.string.profile_email
                )
            }
        )

    fun logout() {
        //fack just clear member info

        viewModelScope.launch {
            _profileInfo.value = ProfileInfo()
            loginRepositoryImpl.logout()
            _hasMemberId.value = false
        }
    }
}