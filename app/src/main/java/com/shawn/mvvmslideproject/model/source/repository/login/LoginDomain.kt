package com.shawn.mvvmslideproject.model.source.repository.login

import com.shawn.mvvmslideproject.ui.login.LoginStatus
import kotlinx.coroutines.flow.Flow

interface LoginDomain {
    suspend fun login(account: String, password: String): Flow<LoginStatus>
    fun register(account: String, password: String)
}