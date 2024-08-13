package com.shawn.mvvmslideproject.model.source.local.login

class Login {
    fun validLogin(account: String, password: String): Boolean {
        if (account.length < 6) {
            return false
        } else if (password.length < 6) {
            return false
        }
        return true
    }
}