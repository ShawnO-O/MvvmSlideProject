package com.shawn.mvvmslideproject.model.source.repository.login

import android.util.Log
import com.shawn.mvvmslideproject.model.room.member.MemberDao
import com.shawn.mvvmslideproject.model.room.member.MemberInfo
import com.shawn.mvvmslideproject.model.source.local.MemberLocalDataSource
import com.shawn.mvvmslideproject.model.source.local.login.LoginLocalDataSource
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import com.shawn.mvvmslideproject.ui.login.LogoutStatus
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
) :
    LoginRepository {
    override suspend fun saveMemberId(mId: Int) {
//            flow{
                memberLocalDataSource.saveId(mId)
//            }
    }

    override suspend fun login(account: String, password: String): Flow<LoginStatus> {
        return flow {
            val status = localDataSource.validLogin(account, password)
            when (status) {
                is LoginStatus.AccountAndPasswordCorrect -> {
                    //這邊要check帳號是否存在
                    var member:MemberInfo?=null
                    withContext(Dispatchers.IO) {
                        member = memberDao.getMemberByAccount(account)
                    }
                    if (member != null) {
                        member?.let {
                            emit(LoginStatus.Success(it.id))
                        }
                    } else {
                        emit(LoginStatus.AccountNotExists("帳號不存在，請先註冊"))
                    }
                }
                else -> {
                    emit(localDataSource.validLogin(account, password))
                }
            }
        }
    }

    override suspend fun logout(): Flow<LogoutStatus> {
        return flow{
            memberLocalDataSource.clearMemberInfo()
        }
    }

    override suspend fun getMembers(): Flow<List<MemberInfo>> {
        return flow {
//            withContext(Dispatchers.IO) {
//            memberDao.insertMember(
//                MemberInfo(
//                    account = "123456",
//                    password = "456789",
//                    latestLoginTime = "2023/05/05"
//                )
//            )
//            }
            var list = memberDao.getAllMember()
            Log.d("shawnTest", "List:$list")
            emit(list)
        }
    }

    override suspend fun register(account: String, password: String): Flow<RegisterStatus> {
        return flow {
            var result = 0L
            withContext(Dispatchers.IO) {
                result = memberDao.insertMember(
                    MemberInfo(
                        account = account,
                        password = password,
                        latestLoginTime = (System.currentTimeMillis() / 100).toString()
                    )
                )
            }

            if(result == -1L){
                emit(RegisterStatus.Fail)
            }else{
                emit(RegisterStatus.Success)
            }
        }
    }
}