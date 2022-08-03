package com.example.applock.utils

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Process
import android.text.TextUtils
import androidx.annotation.RequiresApi
import io.paperdb.Paper

class Utils(context: Context) {
    private var context:Context?= null

    init {
        this.context= context
        Paper.init(context)
    }
    fun isLock(packageName:String):Boolean{
        return Paper.book().read<String>(packageName)  != null
    }
    fun lock(pk:String){
        Paper.book().write(pk,pk)
    }
    fun unLock(pk:String){
        Paper.book().delete(pk)
    }
    fun setLastApp(pk:String){
        Paper.book().write(EXTRA_LAST_APP,pk)
    }
    fun getLastApp():String{
        return Paper.book().read<String>(EXTRA_LAST_APP).toString()
    }
    fun clearLastApp(){
        Paper.book().delete(EXTRA_LAST_APP)
    }
    fun isFavorite(packageName: String):Boolean{
        return Paper.book("Favorite").read<String>(packageName)!= null
    }
    fun favorite(pk:String){
        Paper.book("Favorite").write(pk,pk)
    }
    fun unFavorite(pk: String){
        Paper.book("Favorite").delete(pk)
    }
    fun reset(){
        Paper.book("Favorite").destroy()
    }
    companion object{
        private var EXTRA_LAST_APP = "EXTRA_LAST_APP"
        @Suppress("DEPRECATION")
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
    @Suppress("DEPRECATION")
    fun getLauncherTopApp():String{
        val usageStatsManager:UsageStatsManager = context!!.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = System.currentTimeMillis() - 10000
        var result = ""
        val event = UsageEvents.Event()
        val usageEvents = usageStatsManager.queryEvents(beginTime,endTime)
        while (usageEvents.hasNextEvent()){
            usageEvents.getNextEvent(event)
            if(event.eventType== UsageEvents.Event.MOVE_TO_FOREGROUND){
                result=event.packageName
            }
        }
        if(!TextUtils.isEmpty(result)){
            return result
        }
        return ""
    }
}