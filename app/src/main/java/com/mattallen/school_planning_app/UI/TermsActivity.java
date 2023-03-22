package com.mattallen.school_planning_app.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mattallen.school_planning_app.Database.Repository;
import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.R;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Term> terms = null;
        try {
            terms = repo.getAllTerms();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

    }

    public void addTerm(View v) {
        Intent i = new Intent(TermsActivity.this,TermDetailsActivity.class);
        startActivity(i);
    }

}