package com.findmyjob.android.modules.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.findmyjob.android.R;
import com.findmyjob.android.model.constants.SaveSharedPreference;
import com.findmyjob.android.model.customObjects.UserModel;
import com.findmyjob.android.model.customObjects.UserRoles;
import com.findmyjob.android.modules.employee.ViewJobsActivity;
import com.findmyjob.android.modules.employer.DashboardEmployer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class OTPActivity extends AppCompatActivity {

    public static Intent getStartIntent(Context context, String mobileNumber, String role) {
        Intent intent = new Intent(context, OTPActivity.class);
        intent.putExtra("mobileNumber", mobileNumber);
        intent.putExtra("role", role);
        return intent;
    }

    private Context context;
    private DatabaseReference dbRefUser = FirebaseDatabase.getInstance().getReference("User");
    private Intent targetIntent;
    private String mobileNumber,role;

    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;
    private ProgressBar progressBar;

    //The edit text to input the OTP
    private EditText editTextCode;
    //firebase auth object
    private FirebaseAuth mAuth;
    Button verify_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);
        context = this;
        targetIntent = null;
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        editTextCode = findViewById(R.id.otp_text_view);
        verify_btn = findViewById(R.id.verify_btn);
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        role = getIntent().getStringExtra("role");

        sendVerificationCode(mobileNumber);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editTextCode.getText().toString().trim();
                if ((code.isEmpty() || code.length() < 6)) {
                    editTextCode.setError("Enter Valid Code..");
                    editTextCode.requestFocus();
                    return;
                }
                verify_code(code);
            }

        });

    }

    private void verify_code(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) handleSuccess();

                        else Toast.makeText(context,"Logged in successfully", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void handleSuccess(){
        dbRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    UserModel user = snap.getValue(UserModel.class);
                    if(user!=null && user.getMobileNumber().equals(mobileNumber)){
                        if(user.getRole().equalsIgnoreCase(UserRoles.Employer.toString()))
                        {
                            targetIntent = new Intent(context, DashboardEmployer.class);
                            SaveSharedPreference.setUserId(context,mobileNumber,UserRoles.Employer.toString());
                        }
                        else if(user.getRole().equalsIgnoreCase(UserRoles.Employee.toString())) {
                            targetIntent = new Intent(context, ViewJobsActivity.class);
                            SaveSharedPreference.setUserId(context, mobileNumber, UserRoles.Employee.toString());
                        }
                    }
                }

                if(targetIntent==null) {
                    String key = dbRefUser.push().getKey();
                    if(key!=null){
                        dbRefUser.child(key).setValue(new UserModel(mobileNumber,role));
                        if(role.equalsIgnoreCase(UserRoles.Employer.toString())) {

                            targetIntent = new Intent(context, DashboardEmployer.class);
                            SaveSharedPreference.setUserId(context,mobileNumber,UserRoles.Employer.toString());
                        }
                        else {

                            targetIntent = new Intent(context, ViewJobsActivity.class);
                            SaveSharedPreference.setUserId(context, mobileNumber, UserRoles.Employee.toString());
                        }
                    }
                }
                targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(targetIntent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Cannot connect to database",Toast.LENGTH_LONG).show();
            }
        });
        /*Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/
    }

    private void sendVerificationCode(String mobileNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verify_code(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            startActivity(new Intent(context, RegisterActivity.class));
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}