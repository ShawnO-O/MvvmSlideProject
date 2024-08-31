package com.shawn.mvvmslideproject.ui.login

sealed class LoginStatus {
    //member id
    data class Success(val mId:Int) : LoginStatus()
    data object AccountAndPasswordCorrect : LoginStatus(){}
    data class InvalidAccountIdFormat(val message:String, val statusCode:Int) : LoginStatus()
    data object AccountNotExists: LoginStatus()
    data class InvalidPasswordFormat(val message:String, val statusCode:Int): LoginStatus()
    data object InvalidPassword:LoginStatus()
}

sealed class LogoutStatus{
    data object Success:LogoutStatus()
    data object Fail:LogoutStatus()
}

sealed class RegisterStatus{
    data object AccountAlreadyExist:RegisterStatus()
    data object Success:RegisterStatus()
    data object Fail:RegisterStatus()
}