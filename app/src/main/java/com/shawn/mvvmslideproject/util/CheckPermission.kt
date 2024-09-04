package com.shawn.mvvmslideproject.util

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.shawn.mvvmslideproject.MvvmSlideProjectApplication
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

fun useCamera(
    onCamera:()->Unit,
    onGetPermission: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            MvvmSlideProjectApplication.applicationContext(), android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        onGetPermission()
    } else {
        onCamera()
//        cameraLauncher.launch(uri)
    }
}

fun useAlbum(
    albumLauncher: ManagedActivityResultLauncher<String, Uri?>,
    onGetPermission: () -> Unit
) {
    //在 Android 10 (API level 29) 以上的版本中，讀取相簿不需要 WRITE_EXTERNAL_STORAGE 權限
    if (ContextCompat.checkSelfPermission(
            MvvmSlideProjectApplication.applicationContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            MvvmSlideProjectApplication.applicationContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        onGetPermission()
    } else {
        albumLauncher.launch("image/*")
    }
}


@Composable
fun GetCameraPermission(
    showRationale: Boolean,
    permission: String,
    onGranted: () -> Unit = {},
    onDismiss: () -> Unit
) {
//    var showRationale by remember { mutableStateOf(false) }

    val cameraRequestLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
            } else {
//            showRationale = true
            }
        }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                onGranted()
//                showRationale = true
            } else {
//                showRationale = true
            }
        }

    if (showRationale) {
        NeededPermissionAlertDialog(
            onDismiss = onDismiss,
            launcher = launcher,
            permission = permission,
        )
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}



@Composable
fun GetStoragePermission(
    permission: String, onGranted: () -> Unit = {}
) {
    var showRationale by remember { mutableStateOf(false) }
//    if (showRationale) {
//        NeededPermissionAlertDialog(
//            onDismiss = { showRationale = false },
//            launcher = launcher,
//            permission = permission,
//        )
//    }
}


@Composable
fun NeededPermissionAlertDialog(
    title: String = "需要相機權限",
    text: String = "此應用程式需要相機權限才能拍照。",
    launcher: ActivityResultLauncher<String>,
    permission: String,
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            Button(onClick = {
                launcher.launch(permission)
                onDismiss()
            }) {
                Text("授予權限")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("取消")
            }
        }
    )
}


@Composable
//fun CheckPermission() {
//    val context = LocalContext.current
//    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
//    var showRationale by remember { mutableStateOf(false) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { isGranted: Boolean ->
//            if (isGranted) {
//                // 權限已授予，啟動相機
//                context.takePicture(capturedImageUri) { uri ->
//                    capturedImageUri = uri
//                }
//            } else {
//                // 權限被拒絕，顯示說明或其他處理
//                showRationale = true
//            }
//        }
//    )
//
//
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture(),
//        onResult = { success ->
//            if (success) capturedImageUri
//
////            onCapture(if (success) uri else null)
//        }
//    )
//
//    if (showRationale) {
//        AlertDialog(
//            onDismissRequest = { showRationale = false },
//            title = { Text("需要相機權限") },
//            text = { Text("此應用程式需要相機權限才能拍照。") },
//            confirmButton = {
//                Button(onClick = { launcher.launch(android.Manifest.permission.CAMERA) }) {
//                    Text("授予權限")
//                }
//            },
//            dismissButton = {
//                Button(onClick = { showRationale = false }) {
//                    Text("取消")
//                }
//            }
//        )
//    }
//
//    // 檢查權限並請求
//    val permissionCheckResult =
//        ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
//    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//        // 權限已授予，啟動相機
//        context.takePicture(capturedImageUri) { uri ->
//            capturedImageUri = uri
//        }
//    } else {
//        // 請求權限
//        launcher.launch(android.Manifest.permission.CAMERA)
//    }
//
//    // 顯示拍攝的圖片
//    capturedImageUri?.let { uri ->
//        Image(
//            painter = rememberAsyncImagePainter(uri),
//            contentDescription = null,
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}

fun Context.createImageFile3():File{
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

//// 輔助函數，用於建立圖片檔案
fun Context.createImageFile2(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}
