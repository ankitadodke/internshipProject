package com.findmyjob.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class Profile extends AppCompatActivity {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tabLayout= findViewById(R.id.tablayot_id);
        appBarLayout = findViewById(R.id.appBarid);
        viewPager= findViewById(R.id.view_pager_profile);

        ViewPageAdaptor adaptor = new ViewPageAdaptor(getSupportFragmentManager());
        adaptor.AddFragment(new FragmentPersonal(),"Personal Details");
        adaptor.AddFragment(new FragmentEducation(),"Education Details");

        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);

        MyAccountFragment fragment= new MyAccountFragment();
        FragmentManager manager= getSupportFragmentManager();

    }
}