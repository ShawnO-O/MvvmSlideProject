package com.shawn.mvvmslideproject.model.room.member

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemberDao {
    @Query("SELECT * FROM members")
    fun getAllMember(): List<MemberInfo>

    @Query("SELECT * FROM members WHERE account = :account")
    fun getMemberByAccount(account: String): MemberInfo

    @Query("SELECT * FROM members WHERE account=:account AND password=:password")
    fun checkLoginAccountAndPassword(account: String, password: String): MemberInfo?

    @Insert
    fun insertMember(member: MemberInfo):Long

    @Delete
    fun deleteMember(member: MemberInfo)

    @Update
    fun updateMember(member: MemberInfo)
}