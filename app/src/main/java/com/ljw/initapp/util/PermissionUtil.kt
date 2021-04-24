package com.ljw.initapp.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class PermissionUtil private constructor() {
    fun checkAndRequestPermission(permissionRequestCode: Int, vararg permissions: String): Boolean {
        val requiredPermissions = getRequiredPermissions(*permissions)
        if (requiredPermissions.isNotEmpty() && !mActivity!!.isDestroyed) {
            ActivityCompat.requestPermissions(mActivity!!, requiredPermissions, permissionRequestCode)
        }
        return true
    }

    fun getRequiredPermissions(vararg permissions: String): Array<String> {
        var result: Int
        val permissionList: MutableList<String> = ArrayList()
        for (pm in permissions) {
            result = ContextCompat.checkSelfPermission(mActivity!!, pm)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm)
            }
        }
        return permissionList.toTypedArray()
    }

    fun verifyPermissions(grantResults: IntArray): Boolean {
        if (grantResults.isEmpty()) return false
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    companion object {
        private var mInstance: PermissionUtil? = null
        private var mActivity: Activity? = null
        fun init(activity: Activity?) {
            mInstance = PermissionUtil()
            mActivity = activity
        }

        fun getInstance(): PermissionUtil {
            if (mInstance == null) mInstance = PermissionUtil()
            return mInstance as PermissionUtil
        }
    }
}
