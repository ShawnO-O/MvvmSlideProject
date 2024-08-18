package com.shawn.mvvmslideproject.model.room.profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProfileInfo::class], version = 1)
abstract class ProfileDatabase:RoomDatabase() {
    abstract fun profileDao():ProfileDao

    companion object{
        private var INSTANCE:ProfileDatabase? = null

        fun getInstance(context:Context):ProfileDatabase?{
            if(INSTANCE == null){
                synchronized(ProfileDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ProfileDatabase::class.java,
                        ProfileDatabase::class.java.simpleName
                    ).build()
            }
        }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}