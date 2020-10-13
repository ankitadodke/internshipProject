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

public class HelpAndSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);
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

    public void ClickCallNotPick(View view) {
        ClickCallNotAns(this);
    }

    public void ClickCantLogout(View view) {
        ClickLogout(this);
    }

    public void ClickFakeFraud(View view) {
        ClickFakeCompany(this);
    }

    public void ClickVacancyFull(View view) {
        ClickVacancy(this);
    }

    public void ClickDeleteAcc(View view) {
        ClickDelete(this);
    }

    private void ClickDelete(HelpAndSupport helpAndSupport) {
        AlertDialog.Builder builder = new AlertDialog.Builder(helpAndSupport);
        builder.setTitle("how to delete my account??");
        builder.setMessage("we dont provide this facility delete account.");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    public void ClickConsultancy(View view) {
        ClickConsultancy(this);
    }

    public void ClickCallNotAns(Activity v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v);
        builder.setTitle("Call Not answered??");
        builder.setMessage("you can contact them through mail or you can apply for that job and company will contact you");

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

    public void ClickFakeCompany(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Fake or Fraud Company??");
        builder.setMessage("If you found any fake or fraud company please report us on our mail ");

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
        builder.setTitle("Vacancy Full?");
        builder.setMessage("please apply for new jobs or Contact company HR directly");

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
        builder.setTitle("Consultancy Asking For money??");
        builder.setMessage("We suggest do not pay for any company. If you found any consultancy company please report us on our mail ");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
