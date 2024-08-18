package com.shawn.mvvmslideproject.model.room.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profileInfo")
data class ProfileInfo(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    //this id from member's table id
    val mId:Int = 0,

    val name:String,

    val birthDay:String,

    val gender:String,

    val phone:String,

    val email:String
)