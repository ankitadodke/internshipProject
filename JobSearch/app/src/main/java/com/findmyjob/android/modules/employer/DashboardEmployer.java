package com.findmyjob.android.modules.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.employer.postJob.PostJobActivity;
import com.findmyjob.android.modules.employer.postedJobs.ViewPostedJobsActivity;
import com.findmyjob.android.modules.profile.AddDetails;
import com.findmyjob.android.utils.DeviceUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.Objects;

public class DashboardEmployer extends AppCompatActivity {

    private Context context;
    private LinearLayout lytPostJob,lytPostedJobs;
    DrawerLayout drawerLayout;
    FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    DatabaseReference dbRefJobs = FirebaseDatabase.getInstance().getReference("companyDetails");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_employer_activity);
        context = this;
        drawerLayout = findViewById(R.id.drawerLayout);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (!DeviceUtils.isOnline(context)) {
            //When network is unAvailable

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dailog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            Button btnRetry = dialog.findViewById(R.id.retry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
            dialog.show();


        }
        else {
            checkUser();
        }


        lytPostJob = findViewById(R.id.lytPostJob);
        lytPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PostJobActivity.getStartIntent(context));
            }
        });

        lytPostedJobs = findViewById(R.id.lytPostedJobs);
        lytPostedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ViewPostedJobsActivity.getStartIntent(context));
            }
        });

    }

    private void checkUser() {
        this.fstore.collection("companyDetails").document(Objects.requireNonNull(this.mAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()) {
                    Intent intent = new Intent(context, CompanyProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
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

