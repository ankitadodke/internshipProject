package com.findmyjob.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class employer_register extends AppCompatActivity {

    TextView g1,g2;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private Spinner mCountryCode;
    private EditText mPhoneNumber;

    private Button mGenerateBtn;

    private TextView mLoginFeedbackText;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        layout=findViewById(R.id.layout1);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(employer_register.this, login_employer.class);
                startActivity(intent);


            }
        });


        mCountryCode = findViewById(R.id.country_code_text);
        mCountryCode.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        mPhoneNumber = findViewById(R.id.phone_number_text);
        mGenerateBtn = findViewById(R.id.generate_btn);


        mGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String code = CountryData.countryAreaCodes[mCountryCode.getSelectedItemPosition()];
                String number = mPhoneNumber.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {

                    mLoginFeedbackText.setError("Valid Phone Number Is required");
                    mLoginFeedbackText.requestFocus();
                    return;
                }
                String phonenumber = "+" + code + number;

                Intent intent = new Intent(employer_register.this, otp_employer.class);
                intent.putExtra("mobile", phonenumber);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){

            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }


}