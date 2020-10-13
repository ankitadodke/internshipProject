package com.findmyjob.android.modules.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.findmyjob.android.R;
import com.findmyjob.android.model.constants.SaveSharedPreference;
import com.findmyjob.android.model.customObjects.CompanyDetailsModel;
import com.findmyjob.android.modules.employer.postJob.PostJobActivity;
import com.findmyjob.android.modules.employer.postedJobs.ViewPostedJobsActivity;
import com.findmyjob.android.modules.login.RegisterActivity;
import com.findmyjob.android.utils.DeviceUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DashboardEmployer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FirebaseFirestore fstore;
    StorageReference storageReference;
    ImageView img1;
    TextView userCompany;
    DatabaseReference dbRefJobs = FirebaseDatabase.getInstance().getReference("companyDetails");
    private Context context;
    private LinearLayout lytPostJob, lytPostedJobs;
    private FirebaseAuth mAuth;

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private static void redirectActivity(Activity activity, Class aclass) {

        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_employer_activity);
        context = this;
        drawerLayout = findViewById(R.id.drawerLayout);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userCompany = findViewById(R.id.userCompany);
        img1 = findViewById(R.id.userLogo);
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("companyDetails/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "logo.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img1);
            }
        });
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


        } else {
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
                } else {
                    userCompany.setText(documentSnapshot.getString("companyName"));

                }
            }
        });
    }

    public void ClickMenuEmp(View view) {
        openDrawer(drawerLayout);
    }

    public void ClickLogoEmp(View view) {
        closeDrawer(drawerLayout);
    }

    public void ClickHomeEmp(View view) {
        recreate();
    }

    public void ClickProfileEmp(View view) {
        redirectActivity(this, CompanyProfile.class);
    }

    public void ClickKnowMore(View view) {
        link(this);
    }

    private void link(DashboardEmployer dashboardEmployer) {
        String str_txt = "<a href=http://www.kumaarakalpa.com>Google</a>";
        TextView link = findViewById(R.id.KssplLink);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(str_txt));
    }

    public void ClickHelpAndSupport(View view) {
        redirectActivity(this, HelpAndSupportEmp.class);
    }

    public void ClickViewApplicationEmp(View view) {
        redirectActivity(this, ViewPostedJobsActivity.class);
    }

    public void ClickPrivacyEmp(View view) {
        redirectActivity(this, PrivacyEmployer.class);
    }

    public void ClickLogoutEmp(View view) {
        logout(this);
    }

    public void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are You sure you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SaveSharedPreference.clearUserName(context);
                startActivity(new Intent(context, RegisterActivity.class));
                finish();

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

    @Override
    protected void onPause() {
        super.onPause();
        DashboardEmployer.closeDrawer(drawerLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Uri imgUri = data.getData();
                img1.setImageURI(imgUri);
            }
        }

    }
}

