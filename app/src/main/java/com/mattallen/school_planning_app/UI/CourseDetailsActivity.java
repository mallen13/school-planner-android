package com.mattallen.school_planning_app.UI;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    int termId;
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
        termId = getIntent().getIntExtra("termId", -1);

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
            assessments = repository.getAllAssessmentsByCourse(courseId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    public void saveCourse(View v) throws InterruptedException {
        Course course;

        //if empty
        if (editName.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(),"Add a title first.");
            return;
        }

        //if id is -1, insert new
        if (courseId == -1) {
            //new id = 0 or last + 1
            List <Course> courses = repository.getAllCourses();
            int length = courses.size();
            int newId;
            try {
                newId = courses.get(length - 1).getCourseId() + 1;
            } catch(Exception e) {
                newId = 1;
            }

            courseId = newId;
            course = new Course(
                    newId,
                    editName.getText().toString(),
                    editStartDate.getText().toString(),
                    editEndDate.getText().toString(),
                    editStatus.getText().toString(),
                    editInstructorName.getText().toString(),
                    editInstructorEmail.getText().toString(),
                    editInstructorPhone.getText().toString(),
                    editNote.getText().toString(),
                    termId
                    );
            repository.insert(course);
            //otherwise update
        } else {
            course = new Course(
                    courseId,
                    editName.getText().toString(),
                    editStartDate.getText().toString(),
                    editEndDate.getText().toString(),
                    editStatus.getText().toString(),
                    editInstructorName.getText().toString(),
                    editInstructorEmail.getText().toString(),
                    editInstructorPhone.getText().toString(),
                    editNote.getText().toString(),
                    termId
            );
            repository.update(course);
        }

        Helpers.showToast(getApplicationContext(),"Item Saved");
    }

    public void deleteCourse(View v) throws InterruptedException {
        Course course;

        //if empty
        if (courseId == -1) {
            Helpers.showToast(getApplicationContext(),"No Item Selected");
            return;
        }

        //check if associated values in recycler view
        List<Assessment> items = repository.getAllAssessmentsByCourse(courseId);
        if (items.size() > 0 ) {
            Helpers.showToast(getApplicationContext(),"Associated assessments, cannot delete");
            return;
        }

        course = new Course(
                courseId,
                editName.getText().toString(),
                editStartDate.getText().toString(),
                editEndDate.getText().toString(),
                editStatus.getText().toString(),
                editInstructorName.getText().toString(),
                editInstructorEmail.getText().toString(),
                editInstructorPhone.getText().toString(),
                editNote.getText().toString(),
                termId
        );
        repository.delete(course);

        Intent i = new Intent(this,TermDetailsActivity.class);
        startActivity(i);
    }

    public void addAssessment(View v) throws InterruptedException {
        //if empty
        if (editName.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(),"Add a title first.");
            return;
        }

        //save current
        this.saveCourse(v);

        Intent i = new Intent(CourseDetailsActivity.this,AssessmentDetailsActivity.class);
        i.putExtra("courseId",courseId);
        startActivity(i);
    }

}