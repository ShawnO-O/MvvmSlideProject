package com.shawn.mvvmslideproject.model.room.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profileInfo")
data class ProfileInfo(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    //this id from member's table id
    //在insert的時候必帶，所以不能編輯
    @ColumnInfo("memberId")
    val memberId:Int = 0,

    var name:String?="",

    var birthDay:String?="",

    var gender:String?="",

    var phone:String?="",

    var email:String?=""
)