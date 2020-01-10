package th.co.mfec.androidsecuritydemo.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat


object PermissionsUtility {
    val PHONE_STATE_PERMISSION = Manifest.permission.READ_PHONE_STATE

    fun checkPermission(permission: String, activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}