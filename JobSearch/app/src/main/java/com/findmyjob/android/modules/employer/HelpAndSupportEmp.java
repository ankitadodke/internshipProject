package com.findmyjob.android.modules.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.findmyjob.android.R;

public class HelpAndSupportEmp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support_emp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void ClickCall(View view) {
        ClickCallNotAns(this);
    }

    public void ClickCantLogoutEmp(View view) {
        ClickLogout(this);
    }

    public void ClickNoApplications(View view) {
        ClickNoApp(this);
    }

    public void ClickNoJobs(View view) {
        ClickVacancy(this);
    }

    public void ClickConsultancy(View view) {
        ClickConsultancy(this);
    }
    public void ClickDeleteAccEmp(View view) {
        ClickDelete(this);
    }

    private void ClickDelete(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("how to delete my account??");
        builder.setMessage("we dont provide this facility to delete account.");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    public void ClickCallNotAns(Activity v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v);
        builder.setTitle("not Receiving calls??");
        builder.setMessage("Please fill proper details of job and proper pay scale to get more calls.");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }


    public void ClickLogout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Not able to logout??");
        builder.setMessage("May be its because of some technical problem. You can clear data of application or re-install the application");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void ClickNoApp(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Not Receiving Applications?");
        builder.setMessage("Please fill proper job description to get more applications ");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void ClickVacancy(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("cant post job?");
        builder.setMessage("you can report this problem on our mail.");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void ClickConsultancy(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Consultancy??");
        builder.setMessage("We suggest do not pay for any company.");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}

