package com.shawn.mvvmslideproject.ui.login

import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.MvvmSlideProjectApplication
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepositoryImpl
import com.shawn.mvvmslideproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
) :
    BaseViewModel() {
    private val _account = MutableSharedFlow<String>()
    val account: SharedFlow<String> = _account

    private val _password = MutableSharedFlow<String>()
    val password: SharedFlow<String> = _password

    fun login(account: String, password: String) {
        viewModelScope.launch {
            loginRepositoryImpl.login(account, password).collect {
                when (it) {
                    is LoginStatus.Success -> {
                        loginRepositoryImpl.saveMemberId(it.mId)
                        _toastShardFlow.emit(MvvmSlideProjectApplication.getStringResource(R.string.account_and_password_correct))
                        _finishSharedFlow.emit(true)
                    }

                    is LoginStatus.InvalidAccountIdFormat -> {
                        _toastShardFlow.emit(it.message)
                    }

                    is LoginStatus.AccountNotExists -> {
                        _toastShardFlow.emit(MvvmSlideProjectApplication.getStringResource(R.string.account_not_exists))
                    }

                    is LoginStatus.InvalidPasswordFormat -> {
                        _toastShardFlow.emit(it.message)
                    }

                    is LoginStatus.InvalidPassword -> {
                        _toastShardFlow.emit(MvvmSlideProjectApplication.getStringResource(R.string.invalid_account_or_password))
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
                        _toastShardFlow.emit(
                            MvvmSlideProjectApplication.getStringResource(
                                R.string.register_success_auto_login
                            )
                        )
                        login(account, password)
                    }

                    is RegisterStatus.Fail -> {
                        _toastShardFlow.emit(
                            MvvmSlideProjectApplication.getStringResource(
                                R.string.register_fail_contact_customer_service
                            )
                        )
                    }

                    is RegisterStatus.AccountAlreadyExist -> {
                        _toastShardFlow.emit(
                            MvvmSlideProjectApplication.getStringResource(
                                R.string.register_account_already_exits
                            )
                        )
                    }
                }
            }
        }
    }

    fun changeAccount(account: String) {
        viewModelScope.launch {
            _account.emit(account)
        }
    }

    fun changePassword(password: String) {
        viewModelScope.launch {
            _password.emit(password)
        }
    }
}