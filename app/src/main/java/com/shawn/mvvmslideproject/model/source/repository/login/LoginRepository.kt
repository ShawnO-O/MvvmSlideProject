package com.shawn.mvvmslideproject.model.source.repository.login

import android.util.Log
import com.shawn.mvvmslideproject.model.source.local.login.LoginLocalDataSource
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(private var localDataSource: LoginLocalDataSource) :
    LoginDomain {
    override suspend fun login(account: String, password: String): Flow<LoginStatus> {
        return flow { emit(localDataSource.validLogin(account, password)) }
    }

    override fun register(account: String, password: String) {

    }
}