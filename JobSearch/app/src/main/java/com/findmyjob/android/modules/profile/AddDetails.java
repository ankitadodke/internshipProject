package com.findmyjob.android.modules.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.dashboard.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddDetails extends AppCompatActivity {
    TextInputEditText name, profession, location, email, address;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_details_activity);
        name = findViewById(R.id.eTxtFirstName);
        profession = findViewById(R.id.profession);
        location = findViewById(R.id.location);
        email = findViewById(R.id.email);
        saveBtn = findViewById(R.id.submit);
        address= findViewById(R.id.address);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

            private Boolean validateName() {
                String val = name.getEditableText().toString();
                if (val.isEmpty()) {
                    name.setError("please enter your name");
                    return false;
                } else {
                    name.setError(null);
                    return true;

                }
            }

            private Boolean validateEmail() {
                String emailInput = email.getEditableText().toString();
                if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    email.setError(null);
                    return true;
                }
                else {
                    email.setError("Please enter valid email address");
                    return false;

                }
            }

            private Boolean validateLocation() {
                String val = location.getEditableText().toString();
                if (val.isEmpty()) {
                    location.setError("please enter your location");
                    return false;
                } else {
                    location.setError(null);
                    return true;

                }
            }

            private Boolean validateProfession() {
                String val = profession.getEditableText().toString();
                if (val.isEmpty()) {
                    profession.setError("please enter your Profession");
                    return false;
                } else {
                    profession.setError(null);
                    return true;

                }
            }

            public void registerUser() {
                if (!validateEmail() | !validateLocation() | !validateProfession() | !validateName()) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                DocumentReference docRef = fStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("name", Objects.requireNonNull(AddDetails.this.name.getText()).toString());
                user.put("profession", Objects.requireNonNull(AddDetails.this.profession.getText()).toString());
                user.put("email", Objects.requireNonNull(AddDetails.this.email.getText()).toString());
                user.put("location", Objects.requireNonNull(AddDetails.this.location.getText()).toString());
                user.put("address",Objects.requireNonNull(AddDetails.this.address.getText()).toString());


                docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("User Profile Created");
                        sb.append(userID);

                        startActivity(new Intent(AddDetails.this, MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("failed to create user");

                    }
                });


            }
        });

    }
}
