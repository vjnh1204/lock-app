package com.example.applock.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import com.example.applock.MainActivity;
import com.example.applock.R;
import com.example.applock.receiver.ReceiverAppLock;

@SuppressWarnings("ALL")
public class ServiceAppLockJObIntent extends JobIntentService {
    private static final int JOB_ID = 15462;
    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "10101";


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

    private void createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 112,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,"App lock background task", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.drawable.lock);
        mBuilder.setChannelId(NOTIF_CHANNEL_ID);
        mBuilder.setContentTitle("App Lock")
                .setContentText("App Lock is running in background")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(pendingIntent);
        startForeground(NOTIF_ID,mBuilder.build());
        BackgroundManager.Companion.getInstance().init(this).startService();
    }
    private void cancelNotification(){
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(NOTIF_ID);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        createNotification();
        runAppLock();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
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
