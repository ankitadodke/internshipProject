package com.findmyjob.android.modules.launcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.findmyjob.android.R;
import com.findmyjob.android.model.constants.SaveSharedPreference;
import com.findmyjob.android.model.customObjects.UserRoles;
import com.findmyjob.android.modules.employee.ViewJobsActivity;
import com.findmyjob.android.modules.employer.DashboardEmployer;
import com.findmyjob.android.modules.login.RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

import static com.findmyjob.android.model.constants.SaveSharedPreference.getUserId;
import static com.findmyjob.android.model.constants.SaveSharedPreference.getUserType;

public class SplashScreen extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;

        //Set animation to elements
        findViewById(R.id.splashImage).setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_animation));
        findViewById(R.id.appTitle).setAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_animation));
        findViewById(R.id.appSubTitle).setAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_animation));

        if ((SaveSharedPreference.getUserId(context).length()) == 0) {
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, RegisterActivity.class));
                    finish();
                }
            }, 1500);
        } else {
            if ((SaveSharedPreference.getUserType(context)).equalsIgnoreCase(UserRoles.Employer.toString())) {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(context, DashboardEmployer.class));
                        finish();
                    }
                }, 1500);
            } else if ((SaveSharedPreference.getUserType(context)).equalsIgnoreCase(UserRoles.Employee.toString())) {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(context, ViewJobsActivity.class));
                        finish();
                    }
                }, 1500);
            }
        }
    }
}