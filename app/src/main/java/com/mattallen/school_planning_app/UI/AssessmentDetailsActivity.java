package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Assessment;
import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.Helpers.Helpers;
import com.mattallen.school_planning_app.R;

import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText editName;
    EditText editEndDate;
    String editCategory;
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
        editEndDate = findViewById(R.id.assessmentEndDate);

        //get intents
        assessmentId = getIntent().getIntExtra("id", -1 );
        name = getIntent().getStringExtra("title");
        category = getIntent().getStringExtra("category");
        endDate = getIntent().getStringExtra("endDate");
        courseId = getIntent().getIntExtra("courseId", -1);

        //set inputs to intent vals
        editName.setText(name);
        editEndDate.setText(endDate);

        repository = new Repository(getApplication());

        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.assessmentCategory);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_types, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(this);

        // Prefill spinner with status value
        int spinnerPosition = sAdapter.getPosition(category);
        spinner.setSelection(spinnerPosition);
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
                    editCategory,
                    editEndDate.getText().toString(),
                    courseId
            );
            repository.insert(assessment);
            //otherwise update
        } else {
            assessment = new Assessment(
                    assessmentId,
                    editName.getText().toString(),
                    editCategory,
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
                editCategory,
                editEndDate.getText().toString(),
                courseId
        );
        repository.delete(assessment);

        Intent i = new Intent(this,CourseDetailsActivity.class);

        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //put Intent to go back here
        List<Course> courses = null;
        Course current = null;

        try {
            courses = repository.getAllCourses();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId() == assessmentId) {
                current = courses.get(i);
            }
        }

        Intent intent = new Intent(this,CourseDetailsActivity.class);

        intent.putExtra("id",current.getCourseId());
        intent.putExtra("title",current.getCourseTitle());
        intent.putExtra("startDate",current.getStartDate());
        intent.putExtra("endDate",current.getEndDate());
        intent.putExtra("status",current.getStatus());
        intent.putExtra("note",current.getCourseNote());
        intent.putExtra("instructorName",current.getInstructorName());
        intent.putExtra("instructorPhone",current.getInstructorPhone());
        intent.putExtra("instructorEmail",current.getInstructorEmail());
        intent.putExtra("termId",current.getTermId());

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        editCategory = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Add your code here
    }
}