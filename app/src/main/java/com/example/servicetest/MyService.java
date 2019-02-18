package com.example.servicetest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private DownloadBinder mBinder = new DownloadBinder();
    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService", "startDownload executed");
        }
        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            return 0;
        }
    }
    public MyService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi;
        pi = PendingIntent.getActivity(this, 0, intent, 0);

        /**
         * 8.0及以上：前台服务
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*create notification channel*/
            String channelId = "1";
            String channelName = "1";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            /*show the notification*/
            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                            R.mipmap.ic_launcher))
                    .setContentIntent(pi)
                    .build();
            startForeground(1, notification);//调用startForeground() 方法后就会让MyService变成一个前台服务， 并在系统状态栏显示出来。
        }
        /**
         * 7.0及以下：前台服务
         */
        else{
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                            R.mipmap.ic_launcher))
                    .setContentIntent(pi)
                    .build();
            startForeground(1, notification);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
