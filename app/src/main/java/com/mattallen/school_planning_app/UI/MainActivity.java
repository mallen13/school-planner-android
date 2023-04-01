package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Assessment;
import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add a button
        LinearLayout tableLayout = findViewById(R.id.mainLinearLayout);

        TableRow tableRow = new TableRow(this);
        tableRow.setGravity(Gravity.CENTER);

        Button button = new Button(this);
        button.setText("SHOW TERMS");

        button.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Set vals
        button.setBackgroundColor(Color.parseColor("#6305dc"));
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setPadding(50,5,50,5);

        // Set onClickListener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTerms(v);
            }
        });

        tableRow.addView(new View(this), new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
        ));
        tableRow.addView(button);
        tableRow.addView(new View(this), new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
        ));

        tableLayout.addView(tableRow);

//        Term term = new Term(1,"01/01/2023","01/01/2023","My Term");
//        Repository repo = new Repository(getApplication());
//        repo.insert(term);
//
//        Course course = new Course(1,"My Course","01/01/2023","02/02/2023","Completed","Mr. Instructor","email@email.com","123-456-7890","note",1);
//        //Repository repo = new Repository(getApplication());
//        repo.insert(course);
//
//        Assessment assessment = new Assessment(0,"My Assessment","Objective Assessment (OA)","01/01/2023",1);
//       // Repository repo = new Repository(getApplication());
//        repo.insert(assessment);

        createNotification("My Title","My Notification");
    }

    public void showTerms(View v) {
        //create intent
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);

        //launch activity
        startActivity(intent);

    }

    public void createNotification(String title, String content) {

        // create a notification channel
        String channelId = "default_channel_2";
        String channelName = "Default Channel_2";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        //create notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.app_icon_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //create explicit intent
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // issue the notification
        notificationManager.notify(0, builder.build());

    }

}