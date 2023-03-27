package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Assessment;
import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.Helpers.Helpers;
import com.mattallen.school_planning_app.R;

import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity {
    EditText editName;
    EditText editEndDate;
    EditText editCategory;
    int assessmentId;
    int courseId;
    String category;
    String endDate;
    String name;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        //get inputs
        editName = findViewById(R.id.assessmentTitleInput);
        editCategory = findViewById(R.id.assessmentCategory);
        editEndDate = findViewById(R.id.assessmentEndDate);

        //get intents
        assessmentId = getIntent().getIntExtra("id", -1 );
        name = getIntent().getStringExtra("title");
        category = getIntent().getStringExtra("category");
        endDate = getIntent().getStringExtra("endDate");
        courseId = getIntent().getIntExtra("courseId", -1);

        //set inputs to intent vals
        editName.setText(name);
        editCategory.setText(category);
        editEndDate.setText(endDate);

        repository = new Repository(getApplication());
    }

    public void saveAssessment(View v) throws InterruptedException {
        Assessment assessment;

        //if id is -1, insert new
        if (assessmentId == -1) {
            //new id = 0 or last + 1
            List <Assessment> assessments = repository.getAllAssessments();
            int length = assessments.size();
            int newId;
            try {
                newId = assessments.get(length - 1).getAssessmentId() + 1;
            } catch(Exception e) {
                newId = 1;
            }
            assessmentId = newId;

            assessment = new Assessment(
                    newId,
                    editName.getText().toString(),
                    editCategory.getText().toString(),
                    editEndDate.getText().toString(),
                    courseId
            );
            repository.insert(assessment);
            //otherwise update
        } else {
            assessment = new Assessment(
                    assessmentId,
                    editName.getText().toString(),
                    editCategory.getText().toString(),
                    editEndDate.getText().toString(),
                    courseId
            );
            repository.update(assessment);
        }

        Helpers.showToast(getApplicationContext(),"Item Saved");
    }

    public void deleteAssessment(View v) throws InterruptedException {
        Assessment assessment;

        //if empty
        if (assessmentId == -1) {
            Helpers.showToast(getApplicationContext(),"No Item Selected");
            return;
        }

        assessment = new Assessment(
                assessmentId,
                editName.getText().toString(),
                editCategory.getText().toString(),
                editEndDate.getText().toString(),
                courseId
        );
        repository.delete(assessment);

        Intent i = new Intent(this,CourseDetailsActivity.class);

        startActivity(i);
    }
}