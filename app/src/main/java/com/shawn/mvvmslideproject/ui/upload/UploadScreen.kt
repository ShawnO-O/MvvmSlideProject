package com.shawn.mvvmslideproject.ui.upload

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shawn.mvvmslideproject.R
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

@Composable
fun UploadScreen() {
    //借用imgur api上傳圖片，實作多線程部分
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .height(200.dp),
                painter = painterResource(id = R.drawable.building),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Upload Screen Feature"
            )
        }
    }
}

//
//// Imgur API response data classes
//data class ImgurResponse(val data: ImgurData, val success: Boolean, val status: Int)
//data class ImgurData(val id: String, val title: String?, val description: String?, val type: String, val link: String)
//
//// Imgur API interface
//interface ImgurApi {
//    @Multipart
//    @POST("3/image")
//    fun uploadImage(
//        @Header("Authorization") clientId: String,
//        @Part image: MultipartBody.Part
//    ): Call<ImgurResponse>
//}
//
//// Imgur uploader class
//class ImgurUploader {
//    private val BASE_URL = "https://api.imgur.com/"
//    private val CLIENT_ID = "YOUR_IMGUR_CLIENT_ID" // Replace with your Imgur client ID
//
//    private val imgurApi: ImgurApi
//
//    init {
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        imgurApi = retrofit.create(ImgurApi::class.java)
//    }
//
//    fun uploadImage(imageFile: File, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
//        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
//        val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
//
//        val call = imgurApi.uploadImage("Client-ID $CLIENT_ID", body)
//
//        call.enqueue(object : Callback<ImgurResponse> {
//            override fun onResponse(call: Call<ImgurResponse>, response: Response<ImgurResponse>) {
//                if (response.isSuccessful) {
//                    val imgurResponse = response.body()
//                    if (imgurResponse != null && imgurResponse.success) {
//                        onSuccess(imgurResponse.data.link)
//                    } else {
//                        onError("Upload failed: ${response.message()}")
//                    }
//                } else {
//                    onError("Upload failed: ${response.code()} ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
//                onError("Upload failed: ${t.message}")
//            }
//        })
//    }
//}
//
//// Usage example in a Composable
//@Composable
//fun ImageUploadScreen() {
//    val context = LocalContext.current
//    var uploadStatus by remember { mutableStateOf<String?>(null) }
//    val imgurUploader = remember { ImgurUploader() }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = {
//            // Assume we have a file to upload
//            val imageFile = File(context.cacheDir, "image_to_upload.jpg")
//            imgurUploader.uploadImage(
//                imageFile,
//                onSuccess = { link ->
//                    uploadStatus = "Upload successful. Image link: $link"
//                },
//                onError = { error ->
//                    uploadStatus = "Upload failed: $error"
//                }
//            )
//        }) {
//            Text("Upload Image to Imgur")
//        }
//
//        uploadStatus?.let {
//            Text(it, modifier = Modifier.padding(16.dp))
//        }
//    }
//}