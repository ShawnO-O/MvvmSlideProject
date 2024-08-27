package com.shawn.mvvmslideproject.model.source.repository.login

import com.shawn.mvvmslideproject.ui.login.LoginStatus
import com.shawn.mvvmslideproject.ui.login.LogoutStatus
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun saveMemberId(mId: Int)
    suspend fun login(account: String, password: String): Flow<LoginStatus>
    fun logout()
    suspend fun register(account: String, password: String): Flow<Any>
}