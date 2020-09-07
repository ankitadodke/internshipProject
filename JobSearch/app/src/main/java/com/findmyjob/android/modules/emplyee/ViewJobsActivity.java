package com.findmyjob.android.modules.emplyee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewJobsActivity extends AppCompatActivity {

    private Context context;
    private TextView txtError;
    ProgressBar progressLoading;
    ArrayList<JobModel> jobsList = new ArrayList<>();
    DatabaseReference dbRefJobs = FirebaseDatabase.getInstance().getReference("jobs");

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ViewJobsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jobs_activity);
        context = this;
        txtError= findViewById(R.id.txtError);

        progressLoading = findViewById(R.id.progressLoading);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RecyclerView recJobsList = findViewById(R.id.recJobsList);
        recJobsList.setLayoutManager(new LinearLayoutManager(context));

        dbRefJobs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    JobModel model = snap.getValue(JobModel.class);
                    jobsList.add(model);
                }
                progressLoading.setVisibility(View.GONE);
                if (jobsList.isEmpty())
                    txtError.setVisibility(View.VISIBLE);
                else
                    recJobsList.setAdapter(new JobListAdapter(jobsList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Unable to find jobs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}

class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder>{

    ArrayList<JobModel> jobsList;
    JobListAdapter(ArrayList<JobModel> list){
        jobsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCompName.setText(jobsList.get(position).companyName);
        holder.txtDesignation.setText(jobsList.get(position).jobTitle);
        holder.txtSalary.setText(jobsList.get(position).payScale);

    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCompName,txtDesignation,txtSalary;

        ViewHolder(View itemView) {
            super(itemView);
            txtCompName = itemView.findViewById(R.id.txtCompanyName);
            txtDesignation = itemView.findViewById(R.id.txtJobTitle);
            txtSalary = itemView.findViewById(R.id.txtSalary);
        }
    }
}