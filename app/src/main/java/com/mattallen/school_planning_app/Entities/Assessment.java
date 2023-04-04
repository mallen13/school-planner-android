package com.mattallen.school_planning_app.Entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String assessmentName;
    private String assessmentCategory;

    public String getAssessmentCategory() {
        return assessmentCategory;
    }

    public void setAssessmentCategory(String assessmentCategory) {
        this.assessmentCategory = assessmentCategory;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    private String assessmentEndDate;

    private String assessmentStartDate;

    public Assessment(int assessmentId, String assessmentName, String assessmentCategory, String startDate, String endDate, int courseId) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.assessmentCategory = assessmentCategory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    private String endDate;
    private String startDate;
    private int courseId;

    public Assessment() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
