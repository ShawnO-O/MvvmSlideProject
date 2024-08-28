package com.shawn.mvvmslideproject.ui.profile

sealed class ProfileSealedStatus {
    data class FormatError(val message:String) : ProfileSealedStatus()
    data class ShouldNotBeEmpty(val message:String) : ProfileSealedStatus()
    data object Success : ProfileSealedStatus()
    data object Failed : ProfileSealedStatus()
}