package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Term term = new Term(0,"03/20/2023","03/25/2023","Test Term");
//        Repository repo = new Repository(getApplication());
//        repo.insert(term);
    }

    public void showTerms(View v) {
        //create intent
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);

        //launch activity
        startActivity(intent);

    }
}