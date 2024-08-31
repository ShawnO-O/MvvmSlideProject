package com.shawn.mvvmslideproject.model.repositoryMockTest

import android.content.SharedPreferences
import app.cash.turbine.test
import com.shawn.mvvmslideproject.model.room.member.MemberDao
import com.shawn.mvvmslideproject.model.room.member.MemberInfo
import com.shawn.mvvmslideproject.model.source.local.MemberLocalDataSource
import com.shawn.mvvmslideproject.model.source.local.login.LoginLocalDataSource
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepositoryImpl
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginRepositoryTest {
    private lateinit var memberLocalDataSource: MemberLocalDataSource
    private lateinit var loginRepository: LoginRepositoryImpl

    private lateinit var memberLocalDataSourceSpyk: MemberLocalDataSource
    private lateinit var loginRepositorySpyk: LoginRepositoryImpl

    private lateinit var loginLocalDataSource: LoginLocalDataSource
    private val memberDao = mockk<MemberDao>(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    val account = "a12345678"
    val password = "a123456789"
    val memberId = 1
    val memberInfo = MemberInfo(memberId, account, password, "")
    private val mockSharedPreferences = mockk<SharedPreferences>()
    private val mockEditor = mockk<SharedPreferences.Editor>(relaxed = true)

    @Before
    fun setup() {
        //both two way are ok...
        //mockk：建立一個完全虛假的物件，你需要定義所有函數的行為。
        memberLocalDataSource = mockk(relaxed = true)
        loginRepository =
            LoginRepositoryImpl(mockk(relaxed = true), memberLocalDataSource, mockk(relaxed = true))
        //spyk：建立一個真實物件的 "間諜"，你可以選擇性地覆蓋某些函數的行為
        memberLocalDataSourceSpyk = spyk()
        loginRepositorySpyk = LoginRepositoryImpl(spyk(), spyk(), spyk())

        loginLocalDataSource = spyk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun saveMemberId() =
        //if your function was suspend function, you need to use runTest
        runTest {
            //also use coEvery for suspend function
            val memberId = 1
            coEvery { loginRepository.saveMemberId(memberId) } just Runs


            loginRepository.saveMemberId(memberId)
            verify(exactly = 1) { memberLocalDataSource.saveId(memberId) }
        }

    @Test
    fun loginSuccessAccountNotExists() = runTest {

        every {
            loginLocalDataSource.validLogin(account, password)
        } returns LoginStatus.AccountAndPasswordCorrect

        coEvery { memberDao.getMemberByAccount(account) } returns memberInfo
        coEvery { memberDao.checkLoginAccountAndPassword(account, password) } returns memberInfo

        loginRepositorySpyk.login(account, password).test {
            assertEquals(LoginStatus.AccountNotExists, awaitItem())
            awaitComplete()
        }
    }


}