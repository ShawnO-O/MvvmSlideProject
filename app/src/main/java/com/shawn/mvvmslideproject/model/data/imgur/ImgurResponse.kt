package com.shawn.mvvmslideproject.model.data.imgur

import com.google.gson.annotations.SerializedName

data class ImgurResponse(
    @SerializedName("data")
    val data:Data?,
    @SerializedName("status")
    val status:Int?,
    @SerializedName("success")
    val success:Boolean?
){
    fun getImageLink():String?{
        return data?.link
    }
}

data class Data(
    val account_id: Int,
    val account_url: String,
    val ad_type: Any,
    val ad_url: Any,
    val animated: Boolean,
    val bandwidth: Int,
    val datetime: Int,
    val deletehash: String,
    val description: Any,
    val favorite: Boolean,
    val has_sound: Boolean,
    val height: Int,
    val hls: String,
    val id: String,
    val in_gallery: Boolean,
    val in_most_viral: Boolean,
    val is_ad: Boolean,
    val link: String,
    val mp4: String,
    val name: String,
    val nsfw: Any,
    val section: Any,
    val size: Int,
    val tags: List<Any>,
    val title: Any,
    val type: String,
    val views: Int,
    val vote: Any,
    val width: Int
)