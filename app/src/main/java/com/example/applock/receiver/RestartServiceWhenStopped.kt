package com.example.applock.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.applock.services.BackgroundManager
import com.example.applock.utils.Utils

class RestartServiceWhenStopped : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        val utils = context?.let { Utils(it) }
        val appRunning = utils!!.getLauncherTopApp()
        if(intent!= null && utils.isLock(appRunning)){
            val type = intent.getStringExtra("type")
            if(type.contentEquals("startLockServiceFromAM")){
                BackgroundManager.getInstance()!!.init(context).startService()
                BackgroundManager.getInstance()!!.init(context).startAlarmManager()
            }
        }
    }

}
