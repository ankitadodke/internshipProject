package com.findmyjob.android.modules.employer.postedJobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobPostModel;
import com.findmyjob.android.modules.profile.PersonalFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ViewApplicantsActivity extends AppCompatActivity {

    final static private String INTENT_JOB = "intentJob";
    ProgressBar progressLoading;
    RecyclerView recApplicants;
    ArrayList<ApplicantModel> applicants = new ArrayList<>();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    JobPostModel postedJob;
    private Context context;
    private TextView txtError;

    public static Intent getStartIntent(Context context, JobPostModel job) {
        Intent intent = new Intent(context, ViewApplicantsActivity.class);
        intent.putExtra(INTENT_JOB, job);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_applicants_activity);
        context = this;

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressLoading = findViewById(R.id.progressLoading);
        txtError = findViewById(R.id.txtError);


        recApplicants = findViewById(R.id.recApplicants);
        recApplicants.setLayoutManager(new LinearLayoutManager(context));

        postedJob = (JobPostModel) getIntent().getSerializableExtra(INTENT_JOB);
        fStore.collection("jobs").document(postedJob.id).collection("applicants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot snap : task.getResult()) {
                        getUserDetails(snap.getString("id"),snap.getString("mobile"));
                    }
                    progressLoading.setVisibility(View.GONE);
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

    private void getUserDetails(final String id, final String mobileNumber) {
        fStore.collection("users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                applicants.add(new ApplicantModel(id,documentSnapshot.getString("name"),mobileNumber,documentSnapshot.getString("email")));
                if (applicants.isEmpty())
                    txtError.setVisibility(View.VISIBLE);
                else
                    recApplicants.setAdapter(new ApplicantsAdapter(applicants));
            }
        });
    }
}

class ApplicantsAdapter extends RecyclerView.Adapter<ApplicantsAdapter.ViewHolder>{

    ArrayList<ApplicantModel> applicantsList;

    ApplicantsAdapter(ArrayList<ApplicantModel> list){
        this.applicantsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_details_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(applicantsList.get(position).name);
        holder.txtEmail.setText(applicantsList.get(position).email);
        holder.txtMobile.setText(applicantsList.get(position).mobile);
    }

    @Override
    public int getItemCount() {
        return applicantsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtMobile, txtEmail;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }
}