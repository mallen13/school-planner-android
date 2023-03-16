package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mattallen.school_planning_app.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }

    public void addTerm(View v) {
        Intent i = new Intent(TermsActivity.this,TermDetailsActivity.class);
        startActivity(i);
    }

}