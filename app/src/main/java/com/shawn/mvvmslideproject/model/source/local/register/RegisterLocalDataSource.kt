package com.shawn.mvvmslideproject.model.source.local.register

import javax.inject.Inject

class RegisterLocalDataSource @Inject constructor(){
    fun validRegister(account: String, password: String): Boolean {
        if (account.length < 6) {
            return false
        } else if (password.length < 6) {
            return false
        }
        return true
    }
}