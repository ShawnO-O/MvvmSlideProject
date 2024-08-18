package com.shawn.mvvmslideproject.model.room.member

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemberDao {
    @Query("SELECT * FROM members")
    fun getAllMember(): List<MemberInfo>

    @Query("SELECT * FROM members WHERE account = :account")
    fun getMemberByAccount(account: String): MemberInfo

    @Insert
    fun insertMember(member: MemberInfo)

    @Delete
    fun deleteMember(member: MemberInfo)
}