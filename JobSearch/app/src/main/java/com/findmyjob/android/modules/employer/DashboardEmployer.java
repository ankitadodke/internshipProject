package com.findmyjob.android.modules.employer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.employer.postJob.PostJobActivity;

public class DashboardEmployer extends AppCompatActivity {

    private Context context;
    private LinearLayout lytPostJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_employer_activity);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lytPostJob = findViewById(R.id.lytPostJob);
        lytPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(PostJobActivity.getStartIntent(context)); }
        });
    }
}