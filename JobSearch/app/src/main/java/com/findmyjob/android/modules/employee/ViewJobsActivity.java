package com.findmyjob.android.modules.employee;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobModel;
import com.findmyjob.android.utils.DeviceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewJobsActivity extends AppCompatActivity {

    private Context context;
    private TextView txtError;
    ProgressBar progressLoading;
    ArrayList<JobModel> jobsList = new ArrayList<>();
    FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    DatabaseReference dbRefJobs = FirebaseDatabase.getInstance().getReference("jobs");

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ViewJobsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jobs_activity);
        context = this;
        txtError = findViewById(R.id.txtError);
        fstore = FirebaseFirestore.getInstance();
        BottomNavigationView bottomNav = findViewById(R.id.curved_navigation);
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setOnNavigationItemSelectedListener(nvListener);
        mAuth = FirebaseAuth.getInstance();
        context = this;

        progressLoading = findViewById(R.id.progressLoading);

        final RecyclerView recJobsList = findViewById(R.id.recJobsList);
        recJobsList.setLayoutManager(new LinearLayoutManager(context));
        if (!DeviceUtils.isOnline(context)) {
            //When network is unaailable

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dailog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            Button btnRetry = dialog.findViewById(R.id.retry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
            dialog.show();


        }

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


    private BottomNavigationView.OnNavigationItemSelectedListener nvListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            return true;
                        case R.id.my_account:
                            startActivity(new Intent(getApplicationContext(), MyAccount.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.settings:
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.applications:
                            startActivity(new Intent(getApplicationContext(), MyCalls.class));
                            overridePendingTransition(0,0);
                            return true;
                    }

                    return false;
                }

            };
}


class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    ArrayList<JobModel> jobsList;

    JobListAdapter(ArrayList<JobModel> list) {
        jobsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCompName.setText(jobsList.get(position).companyName);
        holder.txtDesignation.setText(jobsList.get(position).jobTitle);
        holder.txtSalary.setText(jobsList.get(position).payScale);
        holder.txtJobLocation.setText(jobsList.get(position).jobLocation);
        holder.txtHrName.setText(jobsList.get(position).hrName);
        holder.txtHrContact.setText(jobsList.get(position).hrContact);
        holder.txtHrEmail.setText(jobsList.get(position).hrEmail);

    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCompName, txtDesignation, txtSalary, txtJobLocation, txtHrName, txtHrContact, txtHrEmail;

        ViewHolder(View itemView) {
            super(itemView);
            txtCompName = itemView.findViewById(R.id.txtCompanyName);
            txtDesignation = itemView.findViewById(R.id.txtJobTitle);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            txtJobLocation = itemView.findViewById(R.id.eTxtJobLocation);
            txtHrName = itemView.findViewById(R.id.etxtHrName);
            txtHrContact = itemView.findViewById(R.id.etxtMobileNo);
            txtHrEmail = itemView.findViewById(R.id.etxtmail);


        }
    }
    

}