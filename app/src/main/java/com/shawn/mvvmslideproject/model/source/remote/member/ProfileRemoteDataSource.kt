package com.shawn.mvvmslideproject.model.source.remote.member

import com.shawn.mvvmslideproject.model.data.imgur.ImgurResponse
import com.shawn.mvvmslideproject.model.retrofitManager.imgur.ImgurApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.net.URLEncoder
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(private val imgurApiService: ImgurApiService){
//    suspend fun upLoadImage() :Response<ImgurResponse>{
//        val requestFile: RequestBody = RequestBody.create(
////            mimeStr.toMediaTypeOrNull(),
////            uploadAttachmentData.attachment
//        )
//        val encodeFileName = URLEncoder.encode("fileName","UTF-8")
////        val mutipaartBodyFile: MultipartBody.Part =
////            MultipartBody.Part.createFormData("image",)
//        return imgurApiService.postImage("89538457abd19af",encodeFileName,requestFile)
//    }
}