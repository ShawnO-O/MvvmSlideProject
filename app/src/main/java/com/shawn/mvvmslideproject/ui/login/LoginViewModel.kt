package com.shawn.mvvmslideproject.ui.login

import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.model.room.member.MemberInfo
import com.shawn.mvvmslideproject.model.source.local.MemberLocalDataSource
import com.shawn.mvvmslideproject.model.source.local.login.LoginLocalDataSource
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepositoryImpl
import com.shawn.mvvmslideproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
) :
    BaseViewModel() {
    private val _membersSharedFlow = MutableSharedFlow<List<MemberInfo>>()

    private val _account = MutableSharedFlow<String>()
    val account : SharedFlow<String> = _account

    private val _password = MutableSharedFlow<String>()
    val password : SharedFlow<String> = _password

    val memberSharedFlow: SharedFlow<List<MemberInfo>> = _membersSharedFlow
    fun getAllMember() {
        viewModelScope.launch {
            loginRepositoryImpl.getMembers().collect {
                _membersSharedFlow.emit(it)
            }
        }
    }

    fun login(account: String, password: String) {
        viewModelScope.launch {
            loginRepositoryImpl.login(account, password).collect {
                when (it) {
                    is LoginStatus.Success -> {
                        loginRepositoryImpl.saveMemberId(it.mId)
//                        memberLocalDataSource.saveId(it.mId)
                        _toastShardFlow.emit("登入成功")
                        _finishSharedFlow.emit(true)
                    }

                    is LoginStatus.InvalidAccountId -> {
                        _toastShardFlow.emit(it.message)
                    }

                    is LoginStatus.AccountNotExists -> {
                        _toastShardFlow.emit(it.message)
                    }

                    is LoginStatus.InvalidPassword -> {
                        _toastShardFlow.emit(it.message)
                    }

                    LoginStatus.AccountAndPasswordCorrect -> {

                    }
                }
            }
        }
    }

    fun register(account: String, password: String) {
        viewModelScope.launch {
            loginRepositoryImpl.register(account, password).collect {
                when (it) {
                    is RegisterStatus.Success -> {
                        _toastShardFlow.emit("註冊成功，將自動登入")
                        login(account, password)
                    }

                    RegisterStatus.Fail -> {
                        _toastShardFlow.emit("註冊失敗，我不知道")
                    }
                }
            }
        }
    }

    fun changeAccount(account:String){
        viewModelScope.launch {
            _account.emit(account)
        }
    }

    fun changePassword(password:String){
        viewModelScope.launch {
            _password.emit(password)
        }
    }
}