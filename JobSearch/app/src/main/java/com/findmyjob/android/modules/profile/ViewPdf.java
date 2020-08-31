package com.findmyjob.android.modules.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.findmyjob.android.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ViewPdf extends AppCompatActivity {
    TextView pdfTxt;
    PDFView pdfView;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pdf_activity);
        pdfView= findViewById(R.id.pdfViewer);
        pdfTxt=findViewById(R.id.pdfText);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference mRef= database.getReference("user");

    }
}