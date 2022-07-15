package com.example.applock.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.applock.receiver.ReceiverAppLock;

import java.util.Objects;

public class ServiceAppLock extends IntentService {
    public ServiceAppLock() {
        super("ServiceAppLock");
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
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        runAppLock();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Objects.requireNonNull(BackgroundManager.Companion.getInstance()).init(this).startService();
        Objects.requireNonNull(BackgroundManager.Companion.getInstance()).init(this).startAlarmManager();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onDestroy() {
        Objects.requireNonNull(BackgroundManager.Companion.getInstance()).init(this).startService();
        Objects.requireNonNull(BackgroundManager.Companion.getInstance()).init(this).startAlarmManager();
        super.onDestroy();
    }
}
