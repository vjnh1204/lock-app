package com.example.applock.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.applock.services.BackgroundManager

class BootUpReceiver: BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { BackgroundManager.getInstance()!!.init(it).startService()
            BackgroundManager.getInstance()!!.init(it).startAlarmManager()
        }

    }
}