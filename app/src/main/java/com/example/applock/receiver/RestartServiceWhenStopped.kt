package com.example.applock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.applock.services.BackgroundManager
import com.example.applock.services.ServiceAppLock
import com.example.applock.services.ServiceAppLockJObIntent
import com.example.applock.utils.Utils

class RestartServiceWhenStopped : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onReceive(context: Context?, intent: Intent?) {
        val utils = context?.let { Utils(it) }
        val appRunning = utils!!.getLauncherTopApp()
        if(intent!= null && utils.isLock(appRunning)){
            val type = intent.getStringExtra("type")
            if(type.contentEquals("startLockServiceFromAM")){
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    if(!BackgroundManager.getInstance()!!.init(context).isServiceRunning(ServiceAppLockJObIntent::class.java)){
                        BackgroundManager.getInstance()!!.init(context).startService()
                    }
                }else{
                    if (!BackgroundManager.getInstance()!!.init(context).isServiceRunning(ServiceAppLock::class.java)){
                        BackgroundManager.getInstance()!!.init(context).startService()
                    }
                }
                //repeat
                BackgroundManager.getInstance()!!.init(context).startAlarmManager()
            }
        }
    }

}
