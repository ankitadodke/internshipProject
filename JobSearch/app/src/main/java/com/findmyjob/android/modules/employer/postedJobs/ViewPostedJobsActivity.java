package com.findmyjob.android.modules.employer.postedJobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobPostModel;
import com.findmyjob.android.modules.employee.JobListAdapter;
import com.findmyjob.android.modules.employee.ViewJobsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewPostedJobsActivity extends AppCompatActivity {

    private Context context;
    ProgressBar progressLoading;
    String userId;
    private TextView txtError;
    RecyclerView recPostedJobs;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    ArrayList<JobPostModel> jobsList = new ArrayList<>();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ViewPostedJobsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_posted_jobs_activity);
        context = this;
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressLoading = findViewById(R.id.progressLoading);
        txtError = findViewById(R.id.txtError);

        recPostedJobs = findViewById(R.id.recPostedJobs);
        recPostedJobs.setLayoutManager(new LinearLayoutManager(context));

        userId = mAuth.getCurrentUser().getUid();

        fStore.collection("jobs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot snap : task.getResult())
                        if(snap.toObject(JobPostModel.class).companyId.equals(userId))
                            jobsList.add(snap.toObject(JobPostModel.class));
                    progressLoading.setVisibility(View.GONE);
                    if (jobsList.isEmpty())
                        txtError.setVisibility(View.VISIBLE);
                    else
                        recPostedJobs.setAdapter(new PostedJobsAdapter(jobsList));
                } else {
                    progressLoading.setVisibility(View.GONE);
                    txtError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}