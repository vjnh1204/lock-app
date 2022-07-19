package com.example.applock.services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.example.applock.MainActivity;
import com.example.applock.PatternLockActivity;
import com.example.applock.R;
import com.example.applock.receiver.ReceiverAppLock;

import java.util.Objects;

public class ServiceAppLock extends IntentService {
    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "10101";
    public ServiceAppLock() {
        super("ServiceAppLock");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotification();
        }
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
    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotification() {
        Intent notificationIntent = new Intent(this, PatternLockActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 112,
                notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,"App lock background task", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.drawable.lock);
        mBuilder.setChannelId(NOTIF_CHANNEL_ID);
        mBuilder.setContentTitle("App Lock")
                .setContentText("App Lock is running in background")
                .setAutoCancel(false)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setTimeoutAfter(1000)
                .setFullScreenIntent(pendingIntent,true);

        startForeground(NOTIF_ID,mBuilder.build());
//        BackgroundManager.Companion.getInstance().init(this).startService();
    }
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        runAppLock();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
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
