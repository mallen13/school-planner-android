package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.Helpers.Helpers;
import com.mattallen.school_planning_app.R;

public class TermDetailsActivity extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;

    String name;
    String startDate;
    String endDate;
    int termId;
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        //get inputs
        editName = findViewById(R.id.termTitleInput);
        editStartDate = findViewById(R.id.termStartDate);
        editEndDate = findViewById(R.id.termEndDate);

        //get intents
        termId = getIntent().getIntExtra("id", -1 );
        name = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate =  getIntent().getStringExtra("endDate");

        //set inputs to intent vals
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        repository = new Repository(getApplication());
    }

    public void saveTerm(View v) throws InterruptedException {
        Term term;

        //if id is -1, insert new
        if (termId == -1) {
            int newId = repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermId() + 1;
            term = new Term(newId, editStartDate.getText().toString(), editEndDate.getText().toString(), editName.getText().toString() );
            repository.insert(term);
        //otherwise update
        } else {
            term = new Term(termId, editStartDate.getText().toString(), editEndDate.getText().toString(), editName.getText().toString());
            repository.update(term);
        }

        Helpers.showToast(getApplicationContext(),"Item Saved");
    }

    public void deleteTerm(View v) throws InterruptedException {
        Term term;
        term = new Term(termId, editStartDate.getText().toString(), editEndDate.getText().toString(), editName.getText().toString());
        repository.delete(term);

        Intent i = new Intent(this,TermsActivity.class);
        startActivity(i);
    }

    public void addCourse(View v) {
        Intent i = new Intent(TermDetailsActivity.this,CourseDetailsActivity.class);
        startActivity(i);
    }

}