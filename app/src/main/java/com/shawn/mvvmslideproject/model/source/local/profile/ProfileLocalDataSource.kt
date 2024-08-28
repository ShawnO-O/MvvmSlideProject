package com.shawn.mvvmslideproject.model.source.local.profile

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

//here is mock api client
class ProfileLocalDataSource @Inject constructor() {

    fun isProfileNameCorrect(name: String) =
        isProfileNameNotEmpty(name) && isProfileNameLengthOverOne(name)

    fun isProfileNameNotEmpty(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isProfileNameLengthOverOne(name: String): Boolean {
        return name.length > 1
    }

    fun isProfileGenderCorrect(gender: String): Boolean {
        return gender.isNotEmpty()
    }


    fun isProfileBirthCorrect(birth: String): Boolean {
        return birth.isNotEmpty()
    }

    fun isEmailCorrect(email: String): Boolean {
        if (email.isNotEmpty()) {
            val regEx = "^[A-Za-z0-9+_.-]+@(.+)$"
            val p = Pattern.compile(regEx)
            val m: Matcher = p.matcher(email)

            return m.matches()
        } else {
            return false
        }
    }
}