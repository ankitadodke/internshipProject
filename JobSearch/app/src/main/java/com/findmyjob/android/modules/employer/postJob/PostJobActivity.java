package com.findmyjob.android.modules.employer.postJob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.findmyjob.android.utils.DeviceUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PostJobActivity extends AppCompatActivity {

    private Context context;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    ProgressDialog progressDialog;
    TextInputEditText  eTxtJobLocation, eTxtJobTitle, eTxtSkillsRequired, eTxtPayScale,eTxtHrName,eTxtHrContact,eTxtEmail;
    Button btnSubmit;
    String companyName, location, jobLocation, jobTitle, skillSets, payScale, hrName,hrEmail,hrPhone;
    DatabaseReference dbRefJobs = FirebaseDatabase.getInstance().getReference("jobs");

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PostJobActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_job_activity);
        context = this;
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        eTxtJobLocation = findViewById(R.id.eTxtJobLocation);
        eTxtJobTitle = findViewById(R.id.eTxtJobTitle);
        eTxtSkillsRequired = findViewById(R.id.eTxtSkillsRequired);
        eTxtPayScale = findViewById(R.id.eTxtPayScale);
        btnSubmit = findViewById(R.id.btnPostJob);
        eTxtHrName= findViewById(R.id.HRName);
        eTxtHrContact= findViewById(R.id.etxtMobileNo);
        eTxtEmail=findViewById(R.id.etxtmail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eTxtHrName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                hrName = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtJobLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                jobLocation = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtJobTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                jobTitle = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtJobTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                jobTitle = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtSkillsRequired.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                skillSets = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                hrEmail = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtPayScale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                payScale = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtHrContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                hrPhone = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceUtils.isOnline(context)) {
                    String key = dbRefJobs.push().getKey();
                    if (key != null) {
                        dbRefJobs.child(key).child("jobLocation").setValue(jobLocation);
                        dbRefJobs.child(key).child("jobTitle").setValue(jobTitle);
                        dbRefJobs.child(key).child("skills").setValue(skillSets);
                        dbRefJobs.child(key).child("payScale").setValue(payScale + "/month");
                        dbRefJobs.child(key).child("hrName").setValue(hrName);
                        dbRefJobs.child(key).child("hrContact").setValue(hrPhone);
                        dbRefJobs.child(key).child("hrEmail").setValue(hrEmail);
                    }
                    Toast.makeText(context, "Job requirement posted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validate() {
        btnSubmit.setEnabled( jobLocation != null && !jobLocation.isEmpty()
                && jobTitle != null && !jobTitle.isEmpty() && skillSets != null && !skillSets.isEmpty() && payScale != null && !payScale.isEmpty()
                &&  hrName != null && !hrName.isEmpty() &&  hrPhone != null && !hrPhone.isEmpty() &&  hrEmail != null && !hrEmail.isEmpty()
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}