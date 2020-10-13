package com.findmyjob.android.modules.employer.postJob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.CompanyDetailsModel;
import com.findmyjob.android.model.customObjects.JobDetailsModel;
import com.findmyjob.android.model.customObjects.JobPostModel;
import com.findmyjob.android.utils.DeviceUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class PostJobActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    StorageReference storageReference;
    CompanyDetailsModel companyDetails;
    TextInputEditText eTxtJobLocation, eTxtJobTitle, eTxtSkillsRequired, eTxtPayScale,
            eTxtJobInfo, eTxtJobQualification, eTxtEnglish, eTxtExp, eTxtJobTiming,
            eTxtHrName, eTxtHrContact, eTxtEmail;
    Button btnSubmit;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    String jobLocation, jobTitle, skillSets, payScale, hrName, hrContact, hrEmail, jobInfo, EngReq, Exp, jobTime, qualification;
    DatabaseReference dbRefJobs = FirebaseDatabase.getInstance().getReference("jobs");
    private Context context;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, PostJobActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_job_activity);
        context = this;
        storageReference = FirebaseStorage.getInstance().getReference();
        eTxtJobLocation = findViewById(R.id.eTxtJobLocation);
        eTxtJobTitle = findViewById(R.id.eTxtJobTitle);
        eTxtSkillsRequired = findViewById(R.id.eTxtSkillsRequired);
        eTxtPayScale = findViewById(R.id.eTxtPayScale);
        btnSubmit = findViewById(R.id.btnPostJob);
        eTxtHrName = findViewById(R.id.HRName);
        eTxtJobInfo = findViewById(R.id.eTxtJobInfo);
        eTxtJobQualification = findViewById(R.id.eTxtQualification);
        eTxtEnglish = findViewById(R.id.eTxtEnglish);
        eTxtExp = findViewById(R.id.eTxtExp);
        eTxtJobTiming = findViewById(R.id.eTxtJobTime);
        eTxtHrContact = findViewById(R.id.etxtMobileNo);
        eTxtEmail = findViewById(R.id.etxtmail);
        firebaseAuth = FirebaseAuth.getInstance();


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
        eTxtJobTiming.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                jobTime = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        eTxtJobQualification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                qualification = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        eTxtExp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                Exp = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        eTxtEnglish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                EngReq = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        eTxtJobInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                jobInfo = str.toString().trim();
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
                hrContact = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fStore.collection("companyDetails").document(Objects.requireNonNull(this.firebaseAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snap) {
                if (snap.exists())
                    companyDetails = snap.toObject(CompanyDetailsModel.class);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceUtils.isOnline(context) && companyDetails != null) {
                    String key = fStore.collection("jobs").document().getId();
                    fStore.collection("jobs").document(key).set(new JobPostModel(
                            new JobDetailsModel(jobLocation, jobTitle, skillSets, payScale, hrName, hrContact, hrEmail, jobInfo, EngReq, Exp, jobTime, qualification),
                            companyDetails, firebaseAuth.getCurrentUser().getUid(), key));
                    Toast.makeText(context, "Job requirement posted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validate() {
        btnSubmit.setEnabled(jobLocation != null && !jobLocation.isEmpty()
                && jobTitle != null && !jobTitle.isEmpty() && skillSets != null && !skillSets.isEmpty() && payScale != null && !payScale.isEmpty()
                && hrName != null && !hrName.isEmpty() && hrContact != null && !hrContact.isEmpty() && hrEmail != null && !hrEmail.isEmpty()
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}