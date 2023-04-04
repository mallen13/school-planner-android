package com.mattallen.school_planning_app.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mattallen.school_planning_app.Entities.Course;
import com.mattallen.school_planning_app.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    public CourseAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseItemTextView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context,CourseDetailsActivity.class);

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

                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String name = current.getCourseTitle();
            holder.courseItemView.setText(name);
        }
    }

    public void setCourses(List<Course> Courses) {
        mCourses = Courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }
}
