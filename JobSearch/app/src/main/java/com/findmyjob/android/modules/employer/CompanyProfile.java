package com.findmyjob.android.modules.employer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.CompanyDetailsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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

public class CompanyProfile extends AppCompatActivity {
    ImageView img1, img2;
    TextInputEditText companyName, companyLocation, comapnyDesc, comapnyPerksBenifits, companyType, companyNoOfEmp;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    ProgressDialog progressDialog;
    Context context;
    Button saveBtn;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_profile_activity);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        companyName = findViewById(R.id.eTxtCompanyName);
        comapnyDesc = findViewById(R.id.etxtCompnyDesc);
        companyLocation = findViewById(R.id.eTxtCompanyLocation);
        comapnyPerksBenifits = findViewById(R.id.etxtPerks);
        companyType = findViewById(R.id.etstCompanyType);
        companyNoOfEmp = findViewById(R.id.etxtNoOfEmp);
        saveBtn = findViewById(R.id.saveCompanyProfile);
        Toolbar toolbar = findViewById(R.id.toolbar);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName() | !validateDesc() | !validateLocation() | !validateType() | !validateNoEmp() | !validatePerks()) {
                    return;
                }
                DocumentReference docRef = fStore.collection("companyDetails").document(userID);
                    /*Map<String, Object> user = new HashMap<>();
                    user.put("name", Objects.requireNonNull(CompanyProfile.this.companyName.getText()).toString());
                    user.put("description", Objects.requireNonNull(CompanyProfile.this.comapnyDesc.getText()).toString());
                    user.put("type", Objects.requireNonNull(CompanyProfile.this.companyType.getText()).toString());
                    user.put("perks", Objects.requireNonNull(CompanyProfile.this.comapnyPerksBenifits.getText()).toString());
                    user.put("location", Objects.requireNonNull(CompanyProfile.this.companyLocation.getText()).toString());
                    user.put("employeeCount", Objects.requireNonNull(CompanyProfile.this.companyNoOfEmp.getText()).toString());*/

                docRef.set(new CompanyDetailsModel(Objects.requireNonNull(companyName.getText()).toString().trim(), Objects.requireNonNull(comapnyDesc.getText()).toString().trim(),
                        Objects.requireNonNull(companyType.getText()).toString().trim(), Objects.requireNonNull(comapnyPerksBenifits.getText()).toString().trim(), Objects.requireNonNull(companyLocation.getText()).toString().trim(),
                        Objects.requireNonNull(companyNoOfEmp.getText()).toString().trim())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Company Profile Created");
                        sb.append(userID);
                        startActivity(new Intent(context, DashboardEmployer.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("failed to create company profile");
                    }
                });
            }
        });

        context = this;
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("companyDetails/" + Objects.requireNonNull(fAuth.getCurrentUser()).getUid() + "logo.jpg");
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
        //upload img
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Uploading your  company logo");
        progressDialog.setProgress(0);
        progressDialog.show();
        final StorageReference fileRef = storageReference.child("companyDetails/" + Objects.requireNonNull(fAuth.getCurrentUser()).getUid() + "logo.jpg");
        fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(img1);
                    }
                });

                Toast.makeText(context, "Company logo Uploaded..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Company Logo Uploading failed..", Toast.LENGTH_SHORT).show();

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

    private Boolean validateName() {
        String val = companyName.getEditableText().toString();
        if (val.isEmpty()) {
            companyName.setError("please enter your Company name");
            return false;
        } else {
            companyName.setError(null);
            return true;
        }
    }

    private Boolean validateLocation() {
        String val = companyLocation.getEditableText().toString();
        if (val.isEmpty()) {
            companyLocation.setError("please enter your company location");
            return false;
        } else {
            companyLocation.setError(null);
            return true;

        }
    }

    private Boolean validateDesc() {
        String val = comapnyDesc.getEditableText().toString();
        if (val.isEmpty()) {
            comapnyDesc.setError("please enter your Company description");
            return false;
        } else {
            comapnyDesc.setError(null);
            return true;

        }
    }

    private Boolean validateNoEmp() {
        String val = companyNoOfEmp.getEditableText().toString();
        if (val.isEmpty()) {
            companyNoOfEmp.setError("please enter your Company's no of Employee");
            return false;
        } else {
            companyNoOfEmp.setError(null);
            return true;
        }
    }

    private Boolean validateType() {
        String val = companyType.getEditableText().toString();
        if (val.isEmpty()) {
            companyType.setError("please enter your Company Type");
            return false;
        } else {
            companyType.setError(null);
            return true;
        }
    }

    private Boolean validatePerks() {
        String val = comapnyPerksBenifits.getEditableText().toString();
        if (val.isEmpty()) {
            comapnyPerksBenifits.setError("please enter your Company's perks and Benefits");
            return false;
        } else {
            comapnyPerksBenifits.setError(null);
            return true;
        }
    }
}