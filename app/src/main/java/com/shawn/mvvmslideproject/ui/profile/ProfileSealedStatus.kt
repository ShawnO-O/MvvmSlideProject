package com.shawn.mvvmslideproject.ui.profile

sealed class ProfileSealedStatus {
    data object FormatError : ProfileSealedStatus()
    data class ShouldNotBeEmpty(val type:ResumeFieldType) : ProfileSealedStatus()
    data object Success : ProfileSealedStatus()
    data object Failed : ProfileSealedStatus()
}