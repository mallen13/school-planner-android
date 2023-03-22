package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Assessment;
import com.mattallen.school_planning_app.R;

import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editNote;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;

    String name;
    String startDate;
    String endDate;
    String status;
    String note;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    int courseId;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        //get inputs
        editName = findViewById(R.id.courseTitleInput);
        editStartDate = findViewById(R.id.courseStartDate);
        editEndDate = findViewById(R.id.courseEndDate);
        editStatus = findViewById(R.id.courseStatus);
        editNote = findViewById(R.id.courseNotes);
        editInstructorName = findViewById(R.id.instructorName);
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editInstructorPhone = findViewById(R.id.instructorPhone);

        //get intents
        courseId = getIntent().getIntExtra("id", -1 );
        name = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate =  getIntent().getStringExtra("endDate");
        status =  getIntent().getStringExtra("status");
        note =  getIntent().getStringExtra("note");
        instructorName =  getIntent().getStringExtra("instructorName");
        instructorPhone =  getIntent().getStringExtra("instructorPhone");
        instructorEmail=  getIntent().getStringExtra("instructorEmail");


        //set inputs to intent vals
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        editStatus.setText(status);
        editNote.setText(note);
        editInstructorName.setText(instructorName);
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);

        repository = new Repository(getApplication());

        //show list of assessments
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        List<Assessment> assessments = null;
        try {
            assessments = repository.getAllAssessments(courseId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    public void addAssessment(View v) {
        Intent i = new Intent(CourseDetailsActivity.this,AssessmentDetailsActivity.class);
        startActivity(i);
    }
}