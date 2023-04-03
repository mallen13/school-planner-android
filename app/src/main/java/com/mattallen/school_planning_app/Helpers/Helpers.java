package com.mattallen.school_planning_app.Helpers;

import android.accessibilityservice.AccessibilityService;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mattallen.school_planning_app.R;
import com.mattallen.school_planning_app.UI.MainActivity;
import com.mattallen.school_planning_app.UI.MyNotificationPublisher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

abstract public class Helpers {

    private static int numAlert = 0;

    public static void showToast(Context myContext, String text) {
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(myContext, text, duration);
        toast.show();
    }

    public static void createNotification(Context myContext, String title, String content, String dateString) {
        // Parse the date string into a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Create an intent to launch when the notification is triggered
        Intent intent = new Intent(myContext, MyNotificationPublisher.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(myContext, numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);

        // Schedule the intent to be fired at the desired time
        AlarmManager alarmManager = (AlarmManager) myContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        }

        Helpers.showToast(myContext,"Notification Reminder Set");
    }

//    public static void createNotification(Context context, String title, String content, String dateString) {
//        Date myDate = null;
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.US);
//        try {
//            myDate = format.parse(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return; // exit method if date parsing failed
//        }
//
//        // create a notification channel
//        String channelId = "default_channel";
//        String channelName = "Default Channel";
//        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
//
//        // create an intent to launch the MainActivity
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        // create notification builder
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
//                .setSmallIcon(R.drawable.app_icon_foreground)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent);
//
//        // schedule the notification
//        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
//        notificationIntent.putExtra("notification", builder.build());
//        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(context, numAlert++, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPendingIntent);
//    }





}
