package com.mattallen.school_planning_app.Database;

import android.app.Application;
import android.provider.ContactsContract;

import com.mattallen.school_planning_app.DAO.AssessmentDAO;
import com.mattallen.school_planning_app.DAO.CourseDAO;
import com.mattallen.school_planning_app.DAO.TermDAO;
import com.mattallen.school_planning_app.Entities.Assessment;
import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.Entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;

    private List<Term> allParts;
    private List<Term> allTerms;
    private List<AssessmentDAO> allAssessments;

    private static int NUM_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUM_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    public List<Term> getAllTerms() throws InterruptedException {
        databaseExecutor.execute( ()-> {
            allTerms = mTermDAO.getAllTerms();
        });

        Thread.sleep(1000);
        return allTerms;
    }

    //terms
    public void insert(Term term) {
        databaseExecutor.execute( ()-> {
            mTermDAO.insert(term);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute( ()-> {
            mTermDAO.update(term);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute( ()-> {
            mTermDAO.delete(term);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    //courses
    public void insert(Course course) {
        databaseExecutor.execute( ()-> {
            mCourseDAO.insert(course);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute( ()-> {
            mCourseDAO.update(course);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute( ()-> {
            mCourseDAO.delete(course);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    //assessments
    public void insert(Assessment assessment) {
        databaseExecutor.execute( ()-> {
            mAssessmentDAO.insert(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute( ()-> {
            mAssessmentDAO.update(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute( ()-> {
            mAssessmentDAO.delete(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
