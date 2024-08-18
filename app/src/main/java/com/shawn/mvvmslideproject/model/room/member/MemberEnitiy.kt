package com.shawn.mvvmslideproject.model.room.member

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class MemberInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    val account: String,

    val password: String,

    val latestLoginTime: String
)