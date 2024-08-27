package com.shawn.mvvmslideproject.model.data.imgur

import java.io.File

/**
 * Authorization
 * Client-ID {{clientId}}
 *
 * Body
 * formdata
 * image
 * image/video
 *
 * type
 * file
 *
 * file, url, base64, raw
 *
 * title
 * Simple upload
 *
 * The title of the content
 *
 * description
 * This is a simple image upload in Imgur
 *
 * The description of the content
 * **/
data class ImgurRequest(
    var image: File? = null,
    var title: String? = null,
    var type:String?=null,
    var description: String? = null,
    var albumId: String? = null
)