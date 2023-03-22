package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mattallen.school_planning_app.R;

public class TermDetailsActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        //get inputs
        EditText editName = findViewById(R.id.termTitleInput);
        EditText editStartDate = findViewById(R.id.termStartDate);
        EditText editEndDate = findViewById(R.id.termEndDate);

        //get intents
        String name = getIntent().getStringExtra("title");
        String startDate = getIntent().getStringExtra("startDate");
        String endDate =  getIntent().getStringExtra("endDate");

        //set inputs to intent vals
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
    }

    public void saveTerm(View v) {
        
    }
    public void addCourse(View v) {
        Intent i = new Intent(TermDetailsActivity.this,CourseDetailsActivity.class);
        startActivity(i);
    }
}