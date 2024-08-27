package com.shawn.mvvmslideproject.ui.login

sealed class LoginStatus {
    //member id
    data class Success(val mId:Int) : LoginStatus()
    data object AccountAndPasswordCorrect : LoginStatus(){}
    data class InvalidAccountId(val message:String,val statusCode:Int) : LoginStatus()
    data class AccountNotExists(val message:String): LoginStatus()
    data class InvalidPassword(val message:String,val statusCode:Int): LoginStatus()
}

sealed class LogoutStatus{
    data object Success:LogoutStatus()
    data object Fail:LogoutStatus()
}

sealed class RegisterStatus{
    data object Success:RegisterStatus()
    data object Fail:RegisterStatus()
}