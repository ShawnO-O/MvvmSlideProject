package com.shawn.mvvmslideproject.model.source.local.login

import com.shawn.mvvmslideproject.MvvmSlideProjectApplication
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.ui.login.LoginStatus
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor() {

    fun validLoginId(account: String): Pair<Boolean, String> {
        if (!isStringMustOver6Char(account)) {
            return Pair(false, MvvmSlideProjectApplication.getStringResource(R.string.account_should_over_six_char))
        } else if (!isAccountStartWithAlphabet(account)) {
            return Pair(false, MvvmSlideProjectApplication.getStringResource(R.string.account_should_start_with_alphabet))
        }
        return Pair(true, "")
    }

    fun isAccountStartWithAlphabet(account: String): Boolean {
        return if (account.isNotEmpty())
            account[0].lowercaseChar() in 'a'..'z'
        else false
    }

    fun validPassword(password: String): Pair<Boolean, String> {
        if (!isStringMustOver6Char(password)) {
            return Pair(false, MvvmSlideProjectApplication.getStringResource(R.string.password_should_over_six_char))
        } else if ((!isPasswordContainAlphabet(password) || !isPasswordContainNumber(password))) {
            return Pair(false, MvvmSlideProjectApplication.getStringResource(R.string.password_should_contain_alphabet_and_number))
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
            if (!first) return LoginStatus.InvalidAccountIdFormat(second, 0)
        }

        validPassword(password).apply {
            if (!first) return LoginStatus.InvalidPasswordFormat(second, 0)
        }
        return LoginStatus.AccountAndPasswordCorrect
    }
}