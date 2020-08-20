package com.findmyjob.android.modules.profile;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.dashboard.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        setContentView(R.layout.add_details_activity);
        name=findViewById(R.id.firstName);
        profession=findViewById(R.id.profession);
        location=findViewById(R.id.location);
        email= findViewById(R.id.email);
        saveBtn=findViewById(R.id.submit);
        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userID= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()||profession.getText().toString().isEmpty()||location.getText().toString().isEmpty()||email.getText().toString().isEmpty()){
                    Toast.makeText(AddDetails.this, "This fields are mandatory", Toast.LENGTH_LONG).show();
                }

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
                        startActivity(new Intent(AddDetails.this, MainActivity.class));
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
