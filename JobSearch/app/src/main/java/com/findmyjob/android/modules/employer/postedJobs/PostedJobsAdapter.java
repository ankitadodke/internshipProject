package com.findmyjob.android.modules.employer.postedJobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobPostModel;
import com.findmyjob.android.modules.employee.ViewJobDetailsActivity;

import java.util.ArrayList;

public class PostedJobsAdapter extends RecyclerView.Adapter<PostedJobsAdapter.ViewHolder> {

    ArrayList<JobPostModel> jobsList;

    PostedJobsAdapter(ArrayList<JobPostModel> list) {
        jobsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.posted_job_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtDesignation.setText(jobsList.get(position).jobDetails.jobTitle);
        holder.txtSalary.setText(jobsList.get(position).jobDetails.payScale);
        holder.txtJobLocation.setText(jobsList.get(position).jobDetails.jobLocation);
        holder.txtViewApplicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(ViewApplicantsActivity.getStartIntent(view.getContext(), jobsList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDesignation, txtSalary, txtJobLocation,txtViewApplicants;

        ViewHolder(View itemView) {
            super(itemView);
            txtDesignation = itemView.findViewById(R.id.txtJobTitle);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            txtJobLocation = itemView.findViewById(R.id.eTxtJobLocation);
            txtViewApplicants = itemView.findViewById(R.id.txtViewApplicants);
        }
    }
}
