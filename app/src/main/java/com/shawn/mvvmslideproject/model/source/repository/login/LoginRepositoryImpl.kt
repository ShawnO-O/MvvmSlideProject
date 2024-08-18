package com.shawn.mvvmslideproject.model.source.repository.login

import android.util.Log
import com.shawn.mvvmslideproject.model.room.member.MemberDao
import com.shawn.mvvmslideproject.model.room.member.MemberInfo
import com.shawn.mvvmslideproject.model.source.local.login.LoginLocalDataSource
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepositoryImpl @Inject constructor(
    private var localDataSource: LoginLocalDataSource,
    private val memberDao: MemberDao
) :
    LoginRepository {
    override suspend fun login(account: String, password: String): Flow<LoginStatus> {
        return flow {
            var status = localDataSource.validLogin(account, password)
            when(status){
                is LoginStatus.Success ->{
                    //這邊要check帳號是否存在

                }
                else->{
                    emit(localDataSource.validLogin(account, password))

                }
            }
        }
    }

    override suspend fun getMembers(): Flow<List<MemberInfo>> {
        return flow {
            memberDao.insertMember(MemberInfo(account = "123456", password = "456789", latestLoginTime = "2023/05/05"))
            var list = memberDao.getAllMember()
            Log.d("shawnTest","List:$list")
            emit(list)
        }
    }

    override suspend fun register(account: String, password: String): Flow<Any> {
       return flow{
            memberDao.insertMember(MemberInfo(account = account, password = password, latestLoginTime = (System.currentTimeMillis()/100).toString()))
        }
    }
}