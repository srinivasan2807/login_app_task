package com.example.loginapp.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.ext.SdkExtensions.getExtensionVersion
import android.widget.Toast
import androidx.annotation.RequiresApi


object PermissionUtils {
    fun checkPermissions(activity: Activity): Boolean? {
        val hasPermStorage:Int
        val mPackageManager = activity.packageManager
        hasPermStorage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            mPackageManager.checkPermission(
                Manifest.permission.READ_MEDIA_IMAGES, activity.packageName
            )
        }else {
            mPackageManager.checkPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE, activity.packageName
            )
        }
        return if (hasPermStorage != PackageManager.PERMISSION_GRANTED) { // do stuff
            Toast.makeText(activity, "Please grant permission for pick image from storage", Toast.LENGTH_SHORT).show()
            false
        } else hasPermStorage == PackageManager.PERMISSION_GRANTED
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun isPhotoPickerAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getExtensionVersion(Build.VERSION_CODES.R) >= 2
        } else {
            false
        }
    }
}