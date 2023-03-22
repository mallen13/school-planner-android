package com.mattallen.school_planning_app.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mattallen.school_planning_app.Entities.Term;
import com.mattallen.school_planning_app.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.TermViewHolder> {
    public CourseAdapter(Context context, LayoutInflater mInflater) {
        this.context = context;
        this.mInflater = mInflater;
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context,TermDetailsActivity.class);
                    intent.putExtra("id",current.getTermId());
                    intent.putExtra("title",current.getTermTitle());
                    intent.putExtra("startDate",current.getStartDate());
                    intent.putExtra("endDate",current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term current = mTerms.get(position);
            String name = current.getTermTitle();
            holder.termItemView.setText(name);
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null) {
            return mTerms.size();
        } else {
            return 0;
        }
    }
}
