package com.shawn.mvvmslideproject.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shawn.mvvmslideproject.MvvmSlideProjectApplication


class PermissionUtils {
    companion object {
        fun hasCameraPermission(): Boolean {
            return ContextCompat.checkSelfPermission(MvvmSlideProjectApplication.applicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        }

        fun hasStoragePermission(): Boolean {
            return ContextCompat.checkSelfPermission(MvvmSlideProjectApplication.applicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(MvvmSlideProjectApplication.applicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }

        fun hasNotificationsPernmision():Boolean{
            return ContextCompat.checkSelfPermission(MvvmSlideProjectApplication.applicationContext(),Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        }

//        fun requestCameraPermission(activity: Activity) {
//            requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), PhotoUploadParameter.REQUEST_PERMISSION_CAMERA)
//        }

//        fun requestStoragePermission(activity: Activity) {
//            requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PhotoUploadParameter.REQUEST_PERMISSION_READ_WRITE_STORAGE)
//        }

//        fun requestNotificationsPermission(activity: Activity, keepCheckPassing: ActivityResultLauncher<Intent>){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),REQUEST_PERMISSION_NOTIFICATIONS)
//            }else{
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                intent.data = Uri.fromParts("package", activity.packageName, null)
//                keepCheckPassing.launch(intent)
//            }
//        }

        private fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }


    }
}