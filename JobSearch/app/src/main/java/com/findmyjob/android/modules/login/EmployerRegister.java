package com.findmyjob.android.modules.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.findmyjob.android.model.constants.CountryData;
import com.findmyjob.android.model.constants.SaveSharedPreference;
import com.findmyjob.android.model.customObjects.UserRoles;
import com.findmyjob.android.R;
import com.findmyjob.android.modules.employer.DashboardEmployer;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;

public class EmployerRegister extends AppCompatActivity {

    private Context context;

    private Spinner mCountryCode;
    private EditText mPhoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_register_activity);
        context = this;


        mCountryCode = findViewById(R.id.country_code_text);
        mCountryCode.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        mPhoneNumber = findViewById(R.id.phone_number_text);
        Button mGenerateBtn = findViewById(R.id.btnLogin);


        mGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String code = CountryData.countryAreaCodes[mCountryCode.getSelectedItemPosition()];
                String number = mPhoneNumber.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {

                    mPhoneNumber.setError("Valid Phone Number Is required");
                    mPhoneNumber.requestFocus();
                    return;
                }
                String phonenumber = "+" + code + number;

               startActivity(OTPActivity.getStartIntent(context,phonenumber, UserRoles.Employer.toString()));
                SaveSharedPreference.setUserId(context,phonenumber,UserRoles.Employer.toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){

            Intent intent = new Intent(this, DashboardEmployer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }


}