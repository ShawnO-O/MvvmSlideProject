package com.shawn.mvvmslideproject.model.source.repository.login

import com.shawn.mvvmslideproject.model.room.member.MemberDao
import com.shawn.mvvmslideproject.model.room.member.MemberInfo
import com.shawn.mvvmslideproject.model.source.local.MemberLocalDataSource
import com.shawn.mvvmslideproject.model.source.local.login.LoginLocalDataSource
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import com.shawn.mvvmslideproject.ui.login.RegisterStatus
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepositoryImpl @Inject constructor(
    private var localDataSource: LoginLocalDataSource,
    private val memberLocalDataSource: MemberLocalDataSource,
    private val memberDao: MemberDao
) : LoginRepository {
    private var member: MemberInfo? = null
    private var checkLoginResult: MemberInfo? = null

    override suspend fun saveMemberId(mId: Int) {
        memberLocalDataSource.saveId(mId)
    }

    override suspend fun login(account: String, password: String): Flow<LoginStatus> {
        return flow {
            val status = localDataSource.validLogin(account, password)
            when (status) {
                is LoginStatus.AccountAndPasswordCorrect -> {
                    //這邊要check帳號是否存在
                    withContext(Dispatchers.IO) {
                        member = memberDao.getMemberByAccount(account)
                    }
                    if (member != null) {
                        //帳號存在那就要檢查密碼是否正確
                        withContext(Dispatchers.IO) {
                            checkLoginResult =
                                memberDao.checkLoginAccountAndPassword(account, password)
                        }
                        if (checkLoginResult != null) {
                            member?.let {
                                emit(LoginStatus.Success(it.id))
                            }
                        } else {
                            emit(LoginStatus.InvalidPassword)
                        }
                    } else {
                        emit(LoginStatus.AccountNotExists)
                    }
                }

                else -> {
                    emit(localDataSource.validLogin(account, password))
                }
            }
        }
    }

    override fun logout() {
        memberLocalDataSource.clearMemberInfo()
    }

    override suspend fun register(account: String, password: String): Flow<RegisterStatus> {
        return flow {
            var result = 0L
            var haveAccount = false
            withContext(Dispatchers.IO) {
                haveAccount = isAlreadyHaveAccount(account)
            }
            if (!haveAccount) {
                withContext(Dispatchers.IO) {
                    result = insertMember(
                        MemberInfo(
                            account = account,
                            password = password,
                            latestLoginTime = (System.currentTimeMillis() / 100).toString()
                        )
                    )
                }
                if (result == -1L) {
                    emit(RegisterStatus.Fail)
                } else {
                    emit(RegisterStatus.Success)
                }
            } else {
                emit(RegisterStatus.AccountAlreadyExist)
            }
        }
    }

    fun isAlreadyHaveAccount(account: String) = memberDao.getMemberByAccount(account) != null

    fun insertMember(memberInfo: MemberInfo) = memberDao.insertMember(memberInfo)
}