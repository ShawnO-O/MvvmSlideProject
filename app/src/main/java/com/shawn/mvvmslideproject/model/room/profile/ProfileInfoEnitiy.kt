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

    //by link
    var headerShot:String?="https://www.pili.com.tw/img/role/640x741/su_1.jpg?v=1711533444",

    var name:String?="",

    var birthDay:String?="",

    var gender:String?="",

    var phone:String?="",

    var email:String?=""
)