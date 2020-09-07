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
    ImageView img1, img2;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    ProgressDialog progressDialog;
    TextInputEditText eTxtCompanyName, eTxtCompanyLocation, eTxtJobLocation, eTxtJobTitle, eTxtSkillsRequired, eTxtPayScale;
    Button btnSubmit;
    String companyName, location, jobLocation, jobTitle, skillSets, payScale;
    DatabaseReference dbRefJobs = FirebaseDatabase.getInstance().getReference("jobs");

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PostJobActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_job_activity);
        context = this;
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        eTxtCompanyName = findViewById(R.id.eTxtCompanyName);
        eTxtCompanyLocation = findViewById(R.id.eTxtCompanyLocation);
        eTxtJobLocation = findViewById(R.id.eTxtJobLocation);
        eTxtJobTitle = findViewById(R.id.eTxtJobTitle);
        eTxtSkillsRequired = findViewById(R.id.eTxtSkillsRequired);
        eTxtPayScale = findViewById(R.id.eTxtPayScale);
        btnSubmit = findViewById(R.id.btnPostJob);

        StorageReference profileRef = storageReference.child("jobs/" + Objects.requireNonNull(fAuth.getCurrentUser()).getUid() + "logo.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img1);
            }
        });


        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent openStorage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openStorage, 1000);
            }

        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eTxtCompanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                companyName = str.toString().trim();
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        eTxtCompanyLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                location = str.toString().trim();
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceUtils.isOnline(context)) {
                    String key = dbRefJobs.push().getKey();
                    if (key != null) {
                        dbRefJobs.child(key).child("companyName").setValue(companyName);
                        dbRefJobs.child(key).child("companyAddress").setValue(location);
                        dbRefJobs.child(key).child("jobLocation").setValue(jobLocation);
                        dbRefJobs.child(key).child("jobTitle").setValue(jobTitle);
                        dbRefJobs.child(key).child("skills").setValue(skillSets);
                        dbRefJobs.child(key).child("payScale").setValue(payScale + " INR per month");
                    }
                    Toast.makeText(context, "Job requirement posted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validate() {
        btnSubmit.setEnabled(companyName != null && !companyName.isEmpty() && location != null && !location.isEmpty() && jobLocation != null && !jobLocation.isEmpty()
                && jobTitle != null && !jobTitle.isEmpty() && skillSets != null && !skillSets.isEmpty() && payScale != null && !payScale.isEmpty()
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Uri imgUri = data.getData();
                img1.setImageURI(imgUri);
                UploadImg(imgUri);
            }
        }

    }

    private void UploadImg(Uri imgUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Uploading your logo of your company");
        progressDialog.setProgress(0);
        progressDialog.show();
        final StorageReference fileRef = storageReference.child("jobs/" + Objects.requireNonNull(fAuth.getCurrentUser()).getUid() + "logo.jpg");
        fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(img1);
                    }
                });

                Toast.makeText(context, "Image Uploaded..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Image Uploading failed..", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
            }
        });
    }


}