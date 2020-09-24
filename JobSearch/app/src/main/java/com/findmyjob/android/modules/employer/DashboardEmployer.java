package com.findmyjob.android.modules.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.employer.postJob.PostJobActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class DashboardEmployer extends AppCompatActivity {

    private Context context;
    private LinearLayout lytPostJob;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_employer_activity);
        context = this;
        drawerLayout = findViewById(R.id.drawerLayout);

        lytPostJob = findViewById(R.id.lytPostJob);
        lytPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PostJobActivity.getStartIntent(context));
            }
        });
    }

    public void ClickMenuEmp(View view) {
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogoEmp(View view) {
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHomeEmp(View view) {
        recreate();
    }

    public void ClickProfileEmp(View view) {
        redirectActivity(this, CompanyProfile.class);
    }

    public void ClickMyCallsEmp(View view) {
        redirectActivity(this, EmployerCallDetails.class);
    }

    public void ClickViewApplicationEmp(View view) {
        redirectActivity(this, ViewJobApplicationsEMP.class);
    }

    public void ClickPrivacyEmp(View view) {
        redirectActivity(this, PrivacyEmployer.class);
    }

    public void ClickLogoutEmp(View view) {
        logout(this);
    }

    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are You sure you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    private static void redirectActivity(Activity activity, Class aclass) {

        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DashboardEmployer.closeDrawer(drawerLayout);
    }
}

