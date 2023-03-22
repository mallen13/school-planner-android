package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Assessment;
import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Term term = new Term(1,"01/01/2023","01/01/2023","My Term");
//        Repository repo = new Repository(getApplication());
//        repo.insert(term);
//
//        Course course = new Course(1,"My Course","01/01/2023","02/02/2023","Completed","Mr. Instructor","email@email.com","123-456-7890","note",1);
//        //Repository repo = new Repository(getApplication());
//        repo.insert(course);
//
//        Assessment assessment = new Assessment(0,"OA","My Assessment","01/01/2023",1);
//       // Repository repo = new Repository(getApplication());
//        repo.insert(assessment);
    }

    public void showTerms(View v) {
        //create intent
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);

        //launch activity
        startActivity(intent);

    }
}