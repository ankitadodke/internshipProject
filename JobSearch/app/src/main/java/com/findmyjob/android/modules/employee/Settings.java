package com.findmyjob.android.modules.employee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.findmyjob.android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        BottomNavigationView bottomNav = findViewById(R.id.curved_navigation);
        bottomNav.setSelectedItemId(R.id.settings);
        bottomNav.setOnNavigationItemSelectedListener(nvListener);
        context=this;
        FirebaseAuth fAuth= FirebaseAuth.getInstance();
        TextView txtLogout = findViewById(R.id.txtLogout);

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
                            startActivity(new Intent(getApplicationContext(), MyAccount.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.settings:
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