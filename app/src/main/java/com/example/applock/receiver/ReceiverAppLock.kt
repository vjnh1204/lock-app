package com.example.applock.receiver

import android.content.*
import android.content.Intent.*
import android.util.Log
import com.example.applock.PatternLockActivity
import com.example.applock.services.BackgroundManager
import com.example.applock.utils.Utils

class ReceiverAppLock : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val utils = context?.let { Utils(it) }
        val appRunning = utils!!.getLauncherTopApp()
        if(utils.isLock(appRunning)){
            if(appRunning != utils.getLastApp()){
                utils.clearLastApp()
                utils.setLastApp(appRunning)
                val i =Intent(context,PatternLockActivity::class.java)
                i.addFlags(FLAG_ACTIVITY_NEW_TASK  or FLAG_ACTIVITY_CLEAR_TASK)
                i.putExtra("broadcast_receiver","broadcast_receiver")
                context.startActivity(i)
                Log.d("AAA","Start Activity")
            }

            BackgroundManager.getInstance()!!.init(context).startService()
            BackgroundManager.getInstance()!!.init(context).startAlarmManager()
        }
    }
}
