package com.example.applock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.applock.PatternLockActivity
import com.example.applock.utils.Utils

class ReceiverAppLock : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onReceive(context: Context?, intent: Intent?) {
        val utils = context?.let { Utils(it) }
        val appRunning = utils!!.getLauncherTopApp()
        if(utils.isLock(appRunning)){
            if(appRunning != utils.getLastApp()){
                utils.clearLastApp()
                utils.setLastApp(appRunning)
                val i =Intent(context,PatternLockActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                i.putExtra("broadcast_receiver","broadcast_receiver")
                context.startActivity(i)
            }
        }
    }

}
