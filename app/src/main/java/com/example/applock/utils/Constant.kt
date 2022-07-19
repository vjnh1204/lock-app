package com.example.applock.utils

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.applock.model.AppInfo
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

object Constant {
    fun getApplications(activity:Activity):ArrayList<AppInfo>{
        val listApp = ArrayList<AppInfo>()
        val manager = activity.packageManager
        val list:ArrayList<ApplicationInfo> = manager!!.getInstalledApplications(PackageManager.GET_META_DATA) as ArrayList<ApplicationInfo>
        for(appInfo in list){
            if(manager.getLaunchIntentForPackage(appInfo.packageName) != null && (activity.packageName != appInfo.packageName)) {
                listApp.add(AppInfo(appInfo.loadIcon(manager),appInfo.packageName,appInfo.loadLabel(manager).toString()))
            }
        }
        //Sort app
        Collections.sort(listApp, DNComparator())
        return listApp
    }
    class DNComparator : Comparator<AppInfo> {
        override fun compare(o1: AppInfo?, o2: AppInfo?): Int {
            val sa = o1?.appName
            val sb = o2?.appName

            return Collator.getInstance().compare(sa,sb)
        }
    }
}