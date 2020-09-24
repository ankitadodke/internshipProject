package com.findmyjob.android.modules.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.dashboard.AllCalls;
import com.findmyjob.android.modules.dashboard.FailedCalls;
import com.findmyjob.android.modules.dashboard.Interviews;
import com.findmyjob.android.modules.profile.ViewPageAdaptor;
import com.google.android.material.tabs.TabLayout;

public class ViewJobDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_details);
        TabLayout tabLayout = findViewById(R.id.tablayot_id);
        ViewPager viewPager = findViewById(R.id.view_pager_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPageAdaptor adaptor = new ViewPageAdaptor(getSupportFragmentManager());
        adaptor.AddFragment(new JobDesc(), "Job Description");
        adaptor.AddFragment(new ViewCompanyProfile(), "Company Profile");
        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
