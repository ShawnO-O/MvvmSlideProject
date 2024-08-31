package com.shawn.mvvmslideproject.model.room.profile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profileInfo where memberId = :memberId")
    fun getProfile(memberId:String):ProfileInfo

    @Insert
    fun insertProfile(profile:ProfileInfo)

    @Delete
    fun deleteProfile(profile:ProfileInfo)

    @Update
    fun updateProfile(profile:ProfileInfo)

    @Query("UPDATE profileInfo SET name = :name where memberId = :memberId")
    fun updateProfileName(name:String,memberId:String)

    @Query("UPDATE profileInfo SET email = :email where memberId = :memberId")
    fun updateProfileEmail(email:String,memberId:String)

    @Query("UPDATE profileInfo SET gender = :gender where memberId = :memberId")
    fun updateGender(gender:String,memberId:String)

    @Query("UPDATE profileInfo SET birthDay = :birthDay where memberId = :memberId")
    fun updateBirth(birthDay:String,memberId:String)
}