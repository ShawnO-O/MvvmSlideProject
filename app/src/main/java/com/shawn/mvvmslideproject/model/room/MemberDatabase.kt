package com.shawn.mvvmslideproject.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shawn.mvvmslideproject.model.room.member.MemberDao
import com.shawn.mvvmslideproject.model.room.member.MemberInfo
import com.shawn.mvvmslideproject.model.room.profile.ProfileDao
import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo

@Database(entities = [MemberInfo::class, ProfileInfo::class], version = 1)
abstract class MemberDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
    abstract fun profileDao(): ProfileDao

    companion object {
        private var INSTANCE: MemberDatabase? = null

        fun getInstance(context: Context): MemberDatabase? {
            if (INSTANCE == null) {
                synchronized(MemberDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MemberDatabase::class.java,
                        MemberDatabase::class.java.simpleName
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}