package com.example.applock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.applock.services.BackgroundManager

class RestartServiceWhenStopped : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        BackgroundManager.getInstance()!!.init(context!!).startService()
    }

}
