package com.shawn.mvvmslideproject.di

import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepository
import com.shawn.mvvmslideproject.model.source.repository.login.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

}