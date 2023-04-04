package com.mattallen.school_planning_app.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mattallen.school_planning_app.DAO.AssessmentDAO;
import com.mattallen.school_planning_app.DAO.CourseDAO;
import com.mattallen.school_planning_app.DAO.TermDAO;
import com.mattallen.school_planning_app.Entities.Assessment;
import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.Entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 6, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null ) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DatabaseBuilder.class,"MySchoolDB")
                        .fallbackToDestructiveMigration()
                        .build();
                }
            }
        }

        return INSTANCE;
    }

}
