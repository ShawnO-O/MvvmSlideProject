package com.shawn.mvvmslideproject.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.model.room.member.MemberInfo
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepositoryImpl
import com.shawn.mvvmslideproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepositoryImpl: LoginRepositoryImpl) : BaseViewModel() {
    val _membersSharedFlow  = MutableSharedFlow<List<MemberInfo>>()
    val memberSharedFlow : SharedFlow<List<MemberInfo>> = _membersSharedFlow
    fun getAllMember(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loginRepositoryImpl.getMembers().collect {
                    _membersSharedFlow.emit(it)
                }
            }
//            _membersSharedFlow.emit(loginRepositoryImpl.getMembers())
//            var a = loginRepositoryImpl.getMembers()
            Log.d("shawnTest","_membersSharedFlow:$_membersSharedFlow")
        }
    }

    fun login(account: String, password: String) {
        viewModelScope.launch {
            loginRepositoryImpl.login(account, password).collect {
                when (it) {
                    is LoginStatus.Success -> {
                        _toastShardFlow.emit("登入成功")
                    }

                    is LoginStatus.InvalidAccountId -> {
                        _toastShardFlow.emit(it.message)
                    }

                    is LoginStatus.AccountIdExists -> {
                        _toastShardFlow.emit(it.message)
                    }

                    is LoginStatus.InvalidPassword -> {
                        _toastShardFlow.emit(it.message)
                    }
                }
            }
        }
    }

    fun register(account: String, password: String) {

    }
}