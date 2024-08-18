package com.shawn.mvvmslideproject.model.room.member

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemberInfo::class], version = 1)
abstract class MemberDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao

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