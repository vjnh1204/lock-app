package com.example.applock.utils

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.content.Context
import android.os.Build
import android.os.Process

object Utils {
    fun permissionCheck(mContext:Context):Boolean{
        val opsManager : AppOpsManager = mContext.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            opsManager.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,Process.myUid(),mContext.packageName)
        } else {
            opsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,Process.myUid(),mContext.packageName)

        }
        return mode == MODE_ALLOWED
    }
}