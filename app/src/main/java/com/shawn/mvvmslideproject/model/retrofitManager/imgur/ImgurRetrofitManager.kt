package com.shawn.mvvmslideproject.model.retrofitManager.imgur

import com.shawn.mvvmslideproject.model.data.urlManager.UrlManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImgurRetrofitManager {
    private var retrofit: Retrofit
    private var imgurApiService: ImgurApiService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val baseUrl = UrlManager.IMGUR_BASE_URL

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

        imgurApiService = retrofit.create(ImgurApiService::class.java)
    }

    @Singleton
    @Provides
    fun getImgurApiService(): ImgurApiService {
        return imgurApiService
    }

}