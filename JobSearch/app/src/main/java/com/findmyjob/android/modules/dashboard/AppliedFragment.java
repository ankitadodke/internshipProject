package com.findmyjob.android.modules.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.profile.EducationFragment;
import com.findmyjob.android.modules.profile.PersonalFragment;
import com.findmyjob.android.modules.profile.ViewPageAdaptor;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class AppliedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.applied_fragment,container,false);
        TabLayout tabLayout = view.findViewById(R.id.tablayot_id);
        ViewPager viewPager = view.findViewById(R.id.view_pager_profile);
        ViewPageAdaptor adaptor = new ViewPageAdaptor(getFragmentManager());
        adaptor.AddFragment(new Interviews(), "Interviews");
        adaptor.AddFragment(new AllCalls(), "All Calls");
        adaptor.AddFragment(new FailedCalls(), "Failed Calls");


        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);
        return view;

    }


}

