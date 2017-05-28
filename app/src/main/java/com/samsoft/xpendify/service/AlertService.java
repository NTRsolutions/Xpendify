package com.samsoft.xpendify.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.activity.miscellaneous.LaunchActivity;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fred on 10-Jan-16.
 */
public class AlertService extends Service {

    public static final int REQUEST_CODE = 0;
    private NotificationManager notificationManager;
    private int NOTIFICATION = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );

        executor.execute(new Runnable() {
            public void run() {
                PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), REQUEST_CODE, pendingIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new NotificationCompat.Builder(getBaseContext())
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_today_white_24dp)
                        .setTicker("NOTIFICATION")
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(getText(R.string.app_name))
                        .setContentText("NOTIFICATION TITLE")
                        .setContentIntent(contentIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .build();
                notificationManager.notify(NOTIFICATION, notification);
            }
        });

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(NOTIFICATION);
    }

    private Intent pendingIntent() {

        return new Intent(this, LaunchActivity.class);
    }
}
