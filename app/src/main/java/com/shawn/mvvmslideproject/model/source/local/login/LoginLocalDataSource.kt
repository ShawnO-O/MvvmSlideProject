package com.shawn.mvvmslideproject.model.source.local.login

import com.shawn.mvvmslideproject.model.room.member.MemberDao
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor() {

    fun validLoginId(account: String): Pair<Boolean, String> {
        if (!isStringMustOver6Char(account)) {
            return Pair(false, "帳號必須大於六個字元")
        } else if (!isAccountStartWithAlphabet(account)) {
            return Pair(false, "帳號開頭必須為英文字母")
        }
        return Pair(true, "")
    }

    fun isAccountStartWithAlphabet(account: String): Boolean {
        return if (account.isNotEmpty())
            account[0].lowercaseChar() in 'a'..'z'
        else false
    }

    fun isLoginIdExist(account: String) {

    }

    fun validPassword(password: String): Pair<Boolean, String> {
        if (!isStringMustOver6Char(password)) {
            return Pair(false, "密碼必須大於六個字元")
        } else if ((!isPasswordContainAlphabet(password) || !isPasswordContainNumber(password))) {
            return Pair(false, "密碼必須包含英文字母及數字")
        }
        return Pair(true, "")
    }

    fun isPasswordContainAlphabet(password: String): Boolean {
        return password.any { it.isLetter() }
    }

    fun isPasswordContainNumber(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    fun isStringMustOver6Char(string: String): Boolean = string.length >= 6

    fun validLogin(account: String, password: String): LoginStatus {
        /** rule
         * account must over 6 char and start with alphabet
         * password must over 6 char and contain alphabet and number
         * **/
        validLoginId(account).apply {
            if (!first) return LoginStatus.InvalidAccountId(second, 0)
        }
        isLoginIdExist(account).apply {

        }
        validPassword(password).apply {
            if (!first) return LoginStatus.InvalidPassword(second, 0)
        }
        return LoginStatus.Success
    }
}