package com.findmyjob.android.modules.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import com.findmyjob.android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView bottomNav =  findViewById(R.id.curved_navigation);
        bottomNav.setOnNavigationItemSelectedListener(nvListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo==null || !networkInfo.isConnected()|| !networkInfo.isAvailable()){
            //When network is unaailable
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dailog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            Button btnRetry = dialog .findViewById(R.id.retry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
            dialog.show();



        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener nvListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.chat:
                            selectedFragment = new ChatFragment();
                            break;
                        case R.id.my_account:
                            selectedFragment = new MyAccountFragment();
                            break;
                        case R.id.settings:
                            selectedFragment = new SettingsFragment();
                            break;
                        case R.id.applications:
                            selectedFragment = new AppliedFragment();
                            break;
                    }
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView= (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}

