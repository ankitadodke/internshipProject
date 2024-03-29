package com.findmyjob.android.modules.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.findmyjob.android.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;

public class AddResume extends AppCompatActivity {
    Button selectFile,btnViewResume,btnCreateResume;
    FirebaseDatabase database;
    Uri pdfUri;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_resume_activity);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        pdfView = findViewById(R.id.pdfViewer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectFile = findViewById(R.id.btnUploadResume);
        btnViewResume = findViewById(R.id.btnViewResume);
        btnCreateResume = findViewById(R.id.btnCreateResume);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddResume.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else
                    ActivityCompat.requestPermissions(AddResume.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }

        });

        storageReference.child("users/" + Objects.requireNonNull(fAuth.getCurrentUser()).getUid() + "resume.pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if(uri!=null) {
                    pdfUri = uri;
                    selectFile.setText(getString(R.string.add_resume_re_upload_file));
                    btnViewResume.setVisibility(View.VISIBLE);
                }
            }
        });

        btnViewResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(Intent.ACTION_VIEW, pdfUri)); }
        });
        btnCreateResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_txt= "<a href=http://www.resumebuild.com>Google</a>";
                TextView link = findViewById(R.id.btnCreateResume);
                link.setMovementMethod(LinkMovementMethod.getInstance());
                link.setText(Html.fromHtml(str_txt));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }



    private void uploadPdf(Uri pdfUri) {
            //upload img
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(true);
            progressDialog.setTitle("Please wait...");
            progressDialog.setProgress(0);
            progressDialog.show();
           progressDialog.setMessage("Uploading your resume");
            final StorageReference fileRef= storageReference.child("users/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid()+"resume.pdf");
            fileRef.putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(AddResume.this,"Your Resume Uploaded..",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddResume.this,"Your Resume Uploading failed..",Toast.LENGTH_SHORT).show();

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




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
            Toast.makeText(AddResume.this,"Please provide permission",Toast.LENGTH_SHORT).show();
    }

    private void selectPdf() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri=data.getData();
            uploadPdf(pdfUri);
        } else {
            Toast.makeText(AddResume.this, "Please select pdf", Toast.LENGTH_SHORT).show();
        }
    }
}