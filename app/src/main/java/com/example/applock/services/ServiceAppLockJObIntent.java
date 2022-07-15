package com.example.applock.services;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.applock.receiver.ReceiverAppLock;

@SuppressWarnings("ALL")
public class ServiceAppLockJObIntent extends JobIntentService {
    private static final int JOB_ID = 15462;
    public static void enqueueWork(Context context,Intent work){
        enqueueWork(context,ServiceAppLockJObIntent.class,JOB_ID,work);
    }
    private void runAppLock(){
        long endTime = System.currentTimeMillis()+210;
        while (System.currentTimeMillis()<endTime){
            synchronized (this){
                try {
                    Intent intent = new Intent(this, ReceiverAppLock.class);
                    sendBroadcast(intent);
                    wait(endTime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        runAppLock();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        BackgroundManager.Companion.getInstance().init(this).startService();
        BackgroundManager.Companion.getInstance().init(this).startAlarmManager();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        BackgroundManager.Companion.getInstance().init(this).startService();
        BackgroundManager.Companion.getInstance().init(this).startAlarmManager();
        super.onDestroy();
    }
}
