package com.shawn.mvvmslideproject.di

import android.content.Context
import androidx.room.Room
import com.shawn.mvvmslideproject.model.room.MemberDatabase
import com.shawn.mvvmslideproject.model.room.member.MemberDao
import com.shawn.mvvmslideproject.model.room.profile.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideMemberDao(memberDatabase: MemberDatabase): MemberDao {
        return memberDatabase.memberDao()
    }

    @Provides
    fun provideProfileDao(memberDatabase: MemberDatabase):ProfileDao{
        return memberDatabase.profileDao()
    }

    @Provides
    @Singleton
    fun provideMemberDatabase(@ApplicationContext context: Context): MemberDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MemberDatabase::class.java,
            name = MemberDatabase::class.java.simpleName
        ).fallbackToDestructiveMigration().build()
    }
}