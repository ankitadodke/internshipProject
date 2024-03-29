package com.findmyjob.android.modules.employee;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobPostModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    ArrayList<JobPostModel> jobsList;
    public JobListAdapter(ArrayList<JobPostModel> list) {
        jobsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtCompName.setText(jobsList.get(position).companyDetails.name);
        holder.txtDesignation.setText(jobsList.get(position).jobDetails.jobTitle);
        holder.txtSalary.setText(jobsList.get(position).jobDetails.payScale);
        holder.txtJobLocation.setText(jobsList.get(position).jobDetails.jobLocation);

        holder.txtApplyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(ViewJobDetailsActivity.getStartIntent(view.getContext(), jobsList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompName, txtDesignation, txtSalary, txtJobLocation,txtApplyNow;
        ImageView companyLogo;

        ViewHolder(View itemView) {
            super(itemView);
            txtCompName = itemView.findViewById(R.id.txtCompanyName);
            txtDesignation = itemView.findViewById(R.id.txtJobTitle);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            txtJobLocation = itemView.findViewById(R.id.eTxtJobLocation);
            txtApplyNow = itemView.findViewById(R.id.txtApplyNow);
            companyLogo = itemView.findViewById(R.id.companyIcon);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference profileRef = storageReference.child("companyDetails/" + (mAuth.getCurrentUser()).getUid() + "logo.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Picasso.get().load(uri).into(companyLogo);
                }
            });
        }
    }
}
