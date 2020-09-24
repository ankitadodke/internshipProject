package com.findmyjob.android.modules.employee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobModel;
import com.findmyjob.android.modules.profile.AddResume;
import com.findmyjob.android.modules.profile.InterviewTips;
import com.findmyjob.android.modules.profile.ProfileActivity;
import com.findmyjob.android.utils.DeviceUtils;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewJobsActivity extends AppCompatActivity {

    private Context context;
    private TextView txtError;
    ProgressBar progressLoading;
    ArrayList<JobModel> jobsList = new ArrayList<>();
    FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
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
        mAuth = FirebaseAuth.getInstance();
        context = this;
        drawerLayout = findViewById(R.id.drawerLayout);
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

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickDashboard(View view) {
        redirectActivity(this, MyAccount.class);
    }

    public void ClickMyCalls(View view) {
        redirectActivity(this, MyCalls.class);
    }

    public void ClickProfile(View view) {
        redirectActivity(this, ProfileActivity.class);
    }

    public void ClickViewJobs(View view) {
        redirectActivity(this, ViewJobDetails.class);
    }

    public void ClickResume(View view) {
        redirectActivity(this, AddResume.class);
    }

    public void ClickPrivacy(View view) {
        redirectActivity(this, PrivacyActivity.class);
    }

    public void ClickInterviewTips(View view) {
        redirectActivity(this, InterviewTips.class);
    }

    public void ClickLogout(View view) {
        logout(this);
    }

    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are Your sure you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    private static void redirectActivity(Activity activity, Class aclass) {

        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewJobsActivity.closeDrawer(drawerLayout);
    }
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

        }
    }
}