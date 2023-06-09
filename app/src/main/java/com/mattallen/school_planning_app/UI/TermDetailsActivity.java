package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.Helpers.Helpers;
import com.mattallen.school_planning_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

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
        repository = new Repository(getApplication());

        //get inputs
        editName = findViewById(R.id.termTitleInput);
        editStartDate = findViewById(R.id.termStartDate);
        editEndDate = findViewById(R.id.termEndDate);

        //get intents
        termId = getIntent().getIntExtra("id", -1 );

        name = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate =  getIntent().getStringExtra("endDate");

        //set values
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        //show list of courses
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        List<Course> courses = null;
        try {
            courses = repository.getAllCoursesByTerm(termId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }

    public boolean saveTerm(View v) throws InterruptedException {
        Term term;

        //validate dates
        if (editStartDate.getText().toString().contains(".") || editEndDate.getText().toString().contains(".") || editStartDate.getText().toString().contains("-") || editEndDate.getText().toString().contains("-")) {
            Helpers.showToast(getApplicationContext(),"Only use slashes in dates");
            return false;
        }

        if (editStartDate.getText().toString().isEmpty() || editEndDate.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(),"Dates cannot be empty.");
            return false;
        }

        //check end date after start date
        String startString = editStartDate.getText().toString();
        String endString = editEndDate.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
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
        if (termId == -1) {
            //new id = 0 or last + 1
            ArrayList<Term> terms = new ArrayList<>(repository.getAllTerms());
            int length = terms.size();
            int newId;
            try {
                newId = terms.get(length - 1).getTermId() + 1;
            } catch(Exception e) {
                newId = 1;
            }

            termId = newId;

            //newId = repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermId() + 1;

            term = new Term(newId, editStartDate.getText().toString(), editEndDate.getText().toString(), editName.getText().toString() );
            repository.insert(term);
            //otherwise update
        } else {
            term = new Term(termId, editStartDate.getText().toString(), editEndDate.getText().toString(), editName.getText().toString());
            repository.update(term);
        }

        Helpers.showToast(getApplicationContext(),"Item Saved");

        return true;
    }

    public void deleteTerm(View v) throws InterruptedException {
        Term term;

        //if empty
        if (termId == -1) {
            Helpers.showToast(getApplicationContext(),"No Item Selected");
            return;
        }

        //check if associated values in recycler view
        List<Course> items = repository.getAllCoursesByTerm(termId);
        if (items.size() > 0 ) {
            Helpers.showToast(getApplicationContext(),"Associated courses, cannot delete");
            return;
        }

        term = new Term(termId, editStartDate.getText().toString(), editEndDate.getText().toString(), editName.getText().toString());
        repository.delete(term);

        Intent i = new Intent(this,TermsActivity.class);
        startActivity(i);
    }

    public void addCourse(View v) throws InterruptedException {
        //if empty
        if (editName.getText().toString().isEmpty()) {
            Helpers.showToast(getApplicationContext(),"Add a title first.");
            return;
        }

        if (this.saveTerm(v) == false) return;
        Intent i = new Intent(TermDetailsActivity.this,CourseDetailsActivity.class);
        i.putExtra("termId",termId);
        startActivity(i);
    }

}