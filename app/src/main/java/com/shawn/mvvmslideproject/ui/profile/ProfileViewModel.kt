package com.shawn.mvvmslideproject.ui.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepositoryImpl
import com.shawn.mvvmslideproject.model.source.repository.profile.ProfileRepositoryImpl
import com.shawn.mvvmslideproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun saveName(name: String) {
        viewModelScope.launch {
            profileRepositoryImpl.updateProfileName(name).collect {
                when(it){
                    is ProfileSealedStatus.Success ->{
                        _toastShardFlow.emit("儲存成功")
                    }
                    is ProfileSealedStatus.Failed ->{
                        //TODO not yet implemented
                    }
                }
            }
        }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch {
            profileRepositoryImpl.updateProfileEmail(email).collect {
                when(it){
                    is ProfileSealedStatus.Success ->{
                        _toastShardFlow.emit("儲存成功")
                    }
                    is ProfileSealedStatus.Failed ->{
                        //TODO not yet implemented
                    }
                }
            }
        }
    }

    fun saveGender(gender: String) {
        Log.d("shawnTest","gender is $gender")
        viewModelScope.launch {
            profileRepositoryImpl.updateProfileGender(gender).collect {
                when(it){
                    is ProfileSealedStatus.Success ->{
                        _toastShardFlow.emit("儲存成功")
                    }
                    is ProfileSealedStatus.Failed ->{
                        //TODO not yet implemented
                    }
                }
            }
        }
    }

    fun logout() {
        //fack just clear member info
        viewModelScope.launch {
            loginRepositoryImpl.logout()
            _hasMemberId.value = false
        }
    }
}