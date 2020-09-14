package com.findmyjob.android.modules.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.profile.AddDetails;
import com.findmyjob.android.modules.profile.AddResume;
import com.findmyjob.android.modules.profile.InterviewTips;
import com.findmyjob.android.modules.profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_activity);
        BottomNavigationView bottomNav = findViewById(R.id.curved_navigation);
        bottomNav.setSelectedItemId(R.id.my_account);
        bottomNav.setOnNavigationItemSelectedListener(nvListener);
        TextView t1 = findViewById(R.id.profile);
        TextView t2 = findViewById(R.id.text2);
        TextView t3 = findViewById(R.id.preferences);
        TextView t4 = findViewById(R.id.interviewTips);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddResume.class);
                startActivity(intent);

            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddDetails.class);
                startActivity(intent);

            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InterviewTips.class);
                startActivity(intent);

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener nvListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            startActivity(new Intent(getApplicationContext(), ViewJobsActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.my_account:
                            return true;
                        case R.id.settings:
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.applications:
                            startActivity(new Intent(getApplicationContext(), MyCalls.class));
                            overridePendingTransition(0,0);
                            return true;
                    }

                    return false;
                }

            };

}