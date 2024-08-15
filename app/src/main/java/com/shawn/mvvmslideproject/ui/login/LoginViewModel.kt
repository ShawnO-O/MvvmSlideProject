package com.shawn.mvvmslideproject.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepository
import com.shawn.mvvmslideproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : BaseViewModel() {
    fun login(account: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(account, password).collect {
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