package com.findmyjob.android.modules.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.findmyjob.android.R;
import com.findmyjob.android.model.constants.CountryData;
import com.findmyjob.android.model.constants.SaveSharedPreference;
import com.findmyjob.android.model.customObjects.UserRoles;
import com.findmyjob.android.modules.employee.ViewJobsActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private Context context;
    private Spinner mCountryCode;
    private EditText eTxtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        context=this;
        LinearLayout layout = findViewById(R.id.layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, EmployerRegister.class));
            }
        });

        mCountryCode = findViewById(R.id.country_code_text);
        mCountryCode.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        eTxtMobile = findViewById(R.id.eTxtMobile);
        final Button btnLogin = findViewById(R.id.btnLogin);

        eTxtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                btnLogin.setEnabled(str!=null && str.toString().trim().length() > 4);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[mCountryCode.getSelectedItemPosition()];
                String number = eTxtMobile.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    eTxtMobile.setError("Please enter mobile number");
                    eTxtMobile.requestFocus();
                    return;
                }
                String phoneNumber = "+" + code + number;
                startActivity(OTPActivity.getStartIntent(context,phoneNumber, UserRoles.Employee.toString()));
                SaveSharedPreference.setUserId(context,phoneNumber,UserRoles.Employee.toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(context, ViewJobsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}