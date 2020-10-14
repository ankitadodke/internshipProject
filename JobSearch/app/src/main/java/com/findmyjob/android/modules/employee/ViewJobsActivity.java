package com.findmyjob.android.modules.employee;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.findmyjob.android.R;
import com.findmyjob.android.model.constants.SaveSharedPreference;
import com.findmyjob.android.model.customObjects.JobPostModel;
import com.findmyjob.android.modules.employer.HelpAndSupport;
import com.findmyjob.android.modules.login.RegisterActivity;
import com.findmyjob.android.modules.profile.AddDetails;
import com.findmyjob.android.modules.profile.AddResume;
import com.findmyjob.android.modules.profile.InterviewTips;
import com.findmyjob.android.modules.profile.ProfileActivity;
import com.findmyjob.android.utils.DeviceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class ViewJobsActivity extends AppCompatActivity {

    ProgressBar progressLoading;
    ArrayList<JobPostModel> jobsList = new ArrayList<>();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DrawerLayout drawerLayout;
    ImageView img1, companyLogo;
    StorageReference storageReference;
    private Context context;
    private TextView txtError, userName;
    private FirebaseAuth mAuth;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ViewJobsActivity.class);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private static void redirectActivity(Activity activity, Class aClass) {

        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jobs_activity);
        context = this;
        txtError = findViewById(R.id.txtError);
        mAuth = FirebaseAuth.getInstance();
        context = this;
        drawerLayout = findViewById(R.id.drawerLayout);
        progressLoading = findViewById(R.id.progressLoading);
        img1 = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img1);
            }
        });
        final RecyclerView recJobsList = findViewById(R.id.recJobsList);
        recJobsList.setLayoutManager(new LinearLayoutManager(context));

        if (!DeviceUtils.isOnline(context)) {
            //When network is unavailable
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

        fStore.collection("jobs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot snap : task.getResult())
                        jobsList.add(snap.toObject(JobPostModel.class));
                    progressLoading.setVisibility(View.GONE);
                    if (jobsList.isEmpty())
                        txtError.setVisibility(View.VISIBLE);
                    else
                        recJobsList.setAdapter(new JobListAdapter(jobsList));
                } else {
                    progressLoading.setVisibility(View.GONE);
                    txtError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void checkUser() {
        fStore.collection("users").document(Objects.requireNonNull(this.mAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()) {
                    Intent intent = new Intent(context, AddDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else
                    userName.setText(documentSnapshot.getString("name"));
            }
        });
    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickDashboard(View view) {
        redirectActivity(this, MyAccount.class);
    }

    public void ClickDetails(View view) {
        redirectActivity(this, AddDetails.class);
    }

    public void ClickKnow(View view) {
        link(this);
    }

    public void ClickHelpAndSupport(View view) {
        redirectActivity(this, HelpAndSupport.class);
    }

    private void link(Activity activity) {
        String str_txt = "<a href=http://www.kumaarakalpa.com>Google</a>";
        TextView link = findViewById(R.id.KssplLink);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(str_txt));
    }

    public void ClickProfile(View view) {
        redirectActivity(this, ProfileActivity.class);
    }

    public void ClickResume(View view) {
        redirectActivity(this, AddResume.class);
    }

    public void ClickPrivacy(View view) {
        redirectActivity(this, PrivacyActivity.class);
    }

    public void ClickInterviewTips(View view) {
        redirectActivity(this, InterviewTips.class);
    }

    public void ClickLogout(View view) {
        logout(this);
    }

    public void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are Your sure you want to logout?");

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
        ViewJobsActivity.closeDrawer(drawerLayout);
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