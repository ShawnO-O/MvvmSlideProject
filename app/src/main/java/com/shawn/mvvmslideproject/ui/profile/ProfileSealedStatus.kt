package com.shawn.mvvmslideproject.ui.profile

sealed class ProfileSealedStatus {
    data object Success : ProfileSealedStatus()
    data object Failed : ProfileSealedStatus()
}