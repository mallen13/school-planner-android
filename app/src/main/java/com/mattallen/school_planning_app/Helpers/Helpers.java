package com.mattallen.school_planning_app.Helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mattallen.school_planning_app.R;

abstract public class Helpers {

    public static void showToast(Context context, String text) {
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static void createNotification(String title, String content) {
//
//        // create a notification channel
//        String channelId = "default_channel";
//        String channelName = "Default Channel";
//        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
//
//        //create notification builder
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.drawable.app_icon_foreground)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        //set intent
//
//        // issue the notification
//        notificationManager.notify(0, builder.build());
    }





}
