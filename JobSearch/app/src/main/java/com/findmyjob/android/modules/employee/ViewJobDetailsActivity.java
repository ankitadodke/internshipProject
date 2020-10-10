package com.findmyjob.android.modules.employee;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobPostModel;
import com.findmyjob.android.modules.employee.jobDescFragments.ViewJobsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ViewJobDetailsActivity extends AppCompatActivity {

    private static final String INTENT_JOB_DETAILS = "jobDetails";
    private Context context;

    public static Intent getStartIntent(Context context, JobPostModel jobDetails) {
        Intent intent = new Intent(context, ViewJobDetailsActivity.class);
        intent.putExtra(INTENT_JOB_DETAILS, jobDetails);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_job_details_activity);
        context = this;
        JobPostModel jobPostModel = (JobPostModel) getIntent().getSerializableExtra(INTENT_JOB_DETAILS);

        TabLayout tabLayout = findViewById(R.id.jobDetailsTab);
        ViewPager viewPager = findViewById(R.id.viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new ViewJobsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, jobPostModel));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            findViewById(R.id.btnCall).performClick();
        else
            Toast.makeText(context, "Please allow permission to make a call", Toast.LENGTH_SHORT).show();
    }
}


