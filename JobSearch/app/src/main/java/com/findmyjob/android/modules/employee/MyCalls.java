package com.findmyjob.android.modules.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.dashboard.AllCalls;
import com.findmyjob.android.modules.dashboard.FailedCalls;
import com.findmyjob.android.modules.dashboard.Interviews;
import com.findmyjob.android.modules.profile.ViewPageAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MyCalls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_calls_activity);
        TabLayout tabLayout = findViewById(R.id.tablayot_id);
        ViewPager viewPager = findViewById(R.id.view_pager_profile);
        BottomNavigationView bottomNav = findViewById(R.id.curved_navigation);
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setOnNavigationItemSelectedListener(nvListener);
        ViewPageAdaptor adaptor = new ViewPageAdaptor(getSupportFragmentManager());
        adaptor.AddFragment(new Interviews(), "Interviews");
        adaptor.AddFragment(new AllCalls(), "All Calls");
        adaptor.AddFragment(new FailedCalls(), "Failed Calls");
        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);
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
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.applications:
                            return true;
                    }

                    return false;
                }

            };
}