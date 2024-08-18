package com.shawn.mvvmslideproject.model.room.profile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profileInfo where mId = :mId")
    fun getProfile(mId:String):ProfileInfo

    @Insert
    fun insertProfile(profile:ProfileInfo)

    @Delete
    fun deleteProfile(profile:ProfileInfo)

    @Update
    fun updateProfile(profile:ProfileInfo)

}