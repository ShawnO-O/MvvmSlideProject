package com.shawn.mvvmslideproject.model.retrofitManager.imgur

import com.shawn.mvvmslideproject.model.data.imgur.ImgurResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurApiService {
    /**
     * Imgur upload Api.
     *
     * @param clientId Imgur Client ID
     * @param image The Image want to upload
     * **/

    @Multipart
    @POST("3/upload")
    fun postImage(
        @Header("Authorization") clientId:String,
        @Part image:MultipartBody.Part
    ): Response<ImgurResponse>
}