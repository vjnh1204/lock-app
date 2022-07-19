package com.example.applock.services

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.applock.receiver.RestartServiceWhenStopped

class BackgroundManager {
    private var context:Context?= null
    companion object{
        private const val period = 5000
        private const val ALARM_ID = 159784
        @SuppressLint("StaticFieldLeak")
        private var instance:BackgroundManager?= null
        fun getInstance():BackgroundManager?{
            return if(instance == null){
                instance = BackgroundManager()
                instance
            } else instance
        }
    }
    fun init(context: Context):BackgroundManager{
        this.context=context
        return this
    }
    @Suppress("DEPRECATION")
    fun isServiceRunning(serviceClass: Class<*>):Boolean{
        val manager: ActivityManager = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(serviceInfo:ActivityManager.RunningServiceInfo in manager.getRunningServices(Int.MAX_VALUE)){
            if(serviceClass.name.equals(serviceInfo.service.className)){
                return true
            }
        }
        return false
    }
    fun startService(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            if(!isServiceRunning(ServiceAppLock::class.java)){
//                val intent= Intent(context,ServiceAppLockJObIntent::class.java)
//                ServiceAppLockJObIntent.enqueueWork(context,intent)
                context!!.startForegroundService(Intent(context,ServiceAppLock::class.java))
            }
        }
        else{
            if (!isServiceRunning(ServiceAppLock::class.java)){
                context!!.startService(Intent(context,ServiceAppLock::class.java))
            }
        }
    }
    fun stopService(serviceClass: Class<*>){
        if(isServiceRunning(serviceClass)){
            context!!.stopService(Intent(context,serviceClass))
        }
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    fun startAlarmManager(){
        val intent = Intent(context, RestartServiceWhenStopped::class.java)
        intent.putExtra("type","startLockServiceFromAM")
        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + period,pendingIntent)
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    fun stopAlarmManager(){
        val intent = Intent(context,RestartServiceWhenStopped::class.java)
        intent.putExtra("type","startLockServiceFromAM")
        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}