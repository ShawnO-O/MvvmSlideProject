package com.shawn.mvvmslideproject.ui.login

sealed class LoginStatus {
    //member id
    data class Success(val mId:Int) : LoginStatus()
    data object AccountAndPasswordCorrect : LoginStatus(){}
    data class InvalidAccountIdFormat(val message:String, val statusCode:Int) : LoginStatus()
    data class AccountNotExists(val message:String): LoginStatus()
    data class InvalidPasswordFormat(val message:String, val statusCode:Int): LoginStatus()
    data class InvalidPassword(val message:String):LoginStatus()
}

sealed class LogoutStatus{
    data object Success:LogoutStatus()
    data object Fail:LogoutStatus()
}

sealed class RegisterStatus{
    data object Success:RegisterStatus()
    data object Fail:RegisterStatus()
}