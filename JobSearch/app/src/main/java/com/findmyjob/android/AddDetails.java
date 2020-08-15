package com.findmyjob.android;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddDetails extends AppCompatActivity {
    EditText name,profession, location,email;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        name=findViewById(R.id.firstName);
        profession=findViewById(R.id.profession);
        location=findViewById(R.id.location);
        email= findViewById(R.id.email);
        saveBtn=findViewById(R.id.submit);
        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userID= fAuth.getCurrentUser().getUid();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef= fStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("name",AddDetails.this.name.getText().toString());
                user.put("profession",AddDetails.this.profession.getText().toString());
                user.put("email",AddDetails.this.email.getText().toString());
                user.put("location",AddDetails.this.location.getText().toString());

                docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        StringBuilder sb= new StringBuilder();
                        sb.append("User Profile Created");
                        sb.append(userID);
                        startActivity(new Intent(AddDetails.this,MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StringBuilder sb= new StringBuilder();
                        sb.append("failed to create user");

                    }
                });


            }
        });


    }
}
