package com.mattallen.school_planning_app.UI;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class CourseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    String editStatus;
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
        editNote = findViewById(R.id.courseNotes);
        editInstructorName = findViewById(R.id.instructorName);
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editInstructorPhone = findViewById(R.id.instructorPhone);

        //get intents
        courseId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        note = getIntent().getStringExtra("note");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        termId = getIntent().getIntExtra("termId", -1);

        //set inputs to intent vals
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
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


        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.courseStatus);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this,
                R.array.course_statuses, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(this);

        // Prefill spinner with status value
        int spinnerPosition = sAdapter.getPosition(status);
        spinner.setSelection(spinnerPosition);

    }

    public boolean saveCourse(View v) throws InterruptedException {
        Course course;

        //if empty
        if (editName.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(), "Add a title first.");
            return false;
        }

        //validate dates
        if (editStartDate.getText().toString().contains(".") || editEndDate.getText().toString().contains(".") || editStartDate.getText().toString().contains("-") || editEndDate.getText().toString().contains("-")) {
            Helpers.showToast(getApplicationContext(), "Only use slashes in dates");
            return false;
        }

        if (editStartDate.getText().toString().isEmpty() || editEndDate.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(), "Dates cannot be empty.");
            return false;
        }

        //check end date after start date
        String startString = editStartDate.getText().toString();
        String endString = editEndDate.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(startString);
            endDate = format.parse(endString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Ensure that end date is after start date
        if (endDate.before(startDate)) {
            // End date is before start date, show an error message and return
            Helpers.showToast(this, "End date must be after start date");
            return false;
        }


        //if id is -1, insert new
        if (courseId == -1) {
            //new id = 0 or last + 1
            List<Course> courses = repository.getAllCourses();
            int length = courses.size();
            int newId;
            try {
                newId = courses.get(length - 1).getCourseId() + 1;
            } catch (Exception e) {
                newId = 1;
            }

            courseId = newId;
            course = new Course(
                    newId,
                    editName.getText().toString(),
                    editStartDate.getText().toString(),
                    editEndDate.getText().toString(),
                    editStatus,
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
                    editStatus,
                    editInstructorName.getText().toString(),
                    editInstructorEmail.getText().toString(),
                    editInstructorPhone.getText().toString(),
                    editNote.getText().toString(),
                    termId
            );
            repository.update(course);
        }

        Helpers.showToast(getApplicationContext(), "Item Saved");

        return true;
    }

    public void deleteCourse(View v) throws InterruptedException {
        Course course;

        //if empty
        if (courseId == -1) {
            Helpers.showToast(getApplicationContext(), "No Item Selected");
            return;
        }

        //check if associated values in recycler view
        List<Assessment> items = repository.getAllAssessmentsByCourse(courseId);
        if (items.size() > 0) {
            Helpers.showToast(getApplicationContext(), "Associated assessments, cannot delete");
            return;
        }

        course = new Course(
                courseId,
                editName.getText().toString(),
                editStartDate.getText().toString(),
                editEndDate.getText().toString(),
                editStatus,
                editInstructorName.getText().toString(),
                editInstructorEmail.getText().toString(),
                editInstructorPhone.getText().toString(),
                editNote.getText().toString(),
                termId
        );
        repository.delete(course);

        Intent i = new Intent(this, TermDetailsActivity.class);
        startActivity(i);
    }

    public void addAssessment(View v) throws InterruptedException {
        //if empty
        if (editName.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(), "Add a title first.");
            return;
        }

        //save current
        if (this.saveCourse(v) == false) return;

        Intent i = new Intent(CourseDetailsActivity.this, AssessmentDetailsActivity.class);
        i.putExtra("courseId", courseId);
        startActivity(i);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();    //Call the back button's method
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //put Intent to go back here
        List<Term> terms = null;
        Term current = null;

        try {
            terms = repository.getAllTerms();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i).getTermId() == termId) {
                current = terms.get(i);
            }
        }

        Intent intent = new Intent(this, TermDetailsActivity.class);
        intent.putExtra("id", current.getTermId());
        intent.putExtra("title", current.getTermTitle());
        intent.putExtra("startDate", current.getStartDate());
        intent.putExtra("endDate", current.getEndDate());

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        editStatus = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Add your code here
    }

    public void shareNote(View v) {
        String note = editName.getText().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, note);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    public void setCourseStartNotification(View v) {
        //validate dates
        if (editStartDate.getText().toString().contains(".") || editStartDate.getText().toString().contains("-")) {
            Helpers.showToast(getApplicationContext(), "Only use slashes in date");
            return;
        }

        if (editEndDate.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(), "Date cannot be empty.");
            return;
        }

        String dateString = editStartDate.getText().toString();

        String msg = '"' + editName.getText().toString() + '"' + " scheduled to start on " + dateString;
        Helpers.createNotification(this, "Course Start Reminder", msg, dateString);
    }

    public void setCourseEndNotification(View v) {
        //validate dates
        if (editEndDate.getText().toString().contains(".") || editEndDate.getText().toString().contains("-")) {
            Helpers.showToast(getApplicationContext(), "Only use slashes in date");
            return;
        }

        if (editStartDate.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(), "Date cannot be empty.");
            return;
        }

        String dateString = editEndDate.getText().toString();


        String msg = '"' + editName.getText().toString() + '"' + " scheduled to end on " + dateString;
        Helpers.createNotification(this, "Course End Reminder", msg, dateString);
    }
}