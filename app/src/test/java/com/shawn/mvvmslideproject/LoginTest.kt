package com.shawn.mvvmslideproject

import com.shawn.mvvmslideproject.model.source.local.login.LoginLocalDataSource
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class LoginTest {
    private lateinit var loginLocalDataSource: LoginLocalDataSource
    @Before
    fun setUp() {
        loginLocalDataSource = LoginLocalDataSource()
    }

    @Test
    fun validIsAccountStartWithAlphabet(){
        Assert.assertEquals(true,loginLocalDataSource.isAccountStartWithAlphabet("a123456"))
        Assert.assertEquals(false,loginLocalDataSource.isAccountStartWithAlphabet("123456"))
        Assert.assertEquals(false,loginLocalDataSource.isAccountStartWithAlphabet("12a3456"))
        Assert.assertEquals(false,loginLocalDataSource.isAccountStartWithAlphabet("12345a6"))
        Assert.assertEquals(false,loginLocalDataSource.isAccountStartWithAlphabet("123456a"))
    }

    @Test
    fun validIsPasswordContainAlphabet(){
        Assert.assertEquals(false,loginLocalDataSource.isPasswordContainAlphabet(""))
        Assert.assertEquals(true,loginLocalDataSource.isPasswordContainAlphabet("a123456"))
        Assert.assertEquals(false,loginLocalDataSource.isPasswordContainAlphabet("123456"))
    }

    @Test
    fun validIsPasswordContainNumber(){
        Assert.assertEquals(false,loginLocalDataSource.isPasswordContainNumber(""))
        Assert.assertEquals(true,loginLocalDataSource.isPasswordContainNumber("a123456"))
        Assert.assertEquals(false,loginLocalDataSource.isPasswordContainNumber("aaaaaaa"))
    }

    @Test
    fun validPassword(){
        Assert.assertEquals(false,loginLocalDataSource.validPassword("").first)
        Assert.assertEquals(false,loginLocalDataSource.validPassword("123").first)
        Assert.assertEquals(false,loginLocalDataSource.validPassword("123456").first)
        Assert.assertEquals(true,loginLocalDataSource.validPassword("123456a").first)
        Assert.assertEquals(true,loginLocalDataSource.validPassword("a123456").first)
    }

    @Test
    fun validStringMustOver6Char(){
        Assert.assertEquals(false,loginLocalDataSource.isStringMustOver6Char("12"))
        Assert.assertEquals(false,loginLocalDataSource.isStringMustOver6Char("123"))
        Assert.assertEquals(false,loginLocalDataSource.isStringMustOver6Char("1234"))
        Assert.assertEquals(false,loginLocalDataSource.isStringMustOver6Char("12345"))
        Assert.assertEquals(true,loginLocalDataSource.isStringMustOver6Char("123456"))
        Assert.assertEquals(true,loginLocalDataSource.isStringMustOver6Char("1234567"))

    }
}