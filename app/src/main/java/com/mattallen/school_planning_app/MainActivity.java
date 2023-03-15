package com.mattallen.school_planning_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showTerms(View v) {
        //create intent
        Intent intent = new Intent(MainActivity.this,TermsActivity.class);

        //launch activity
        startActivity(intent);
    }
}