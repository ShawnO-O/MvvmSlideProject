package com.shawn.mvvmslideproject.ui.login

sealed class LoginStatus {
    data object Success : LoginStatus()
    data class InvalidAccountId(val message:String,val statusCode:Int) : LoginStatus()
    data class AccountIdExists(val message:String): LoginStatus()
    data class InvalidPassword(val message:String,val statusCode:Int): LoginStatus()
}