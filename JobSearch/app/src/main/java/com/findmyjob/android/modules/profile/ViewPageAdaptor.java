package com.findmyjob.android.modules.profile;



import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPageAdaptor extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList =new ArrayList<>();
    private final List<String> fragmentTitle = new ArrayList<>();

    public ViewPageAdaptor(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
    public void  AddFragment (Fragment fragment,String title)
    {
        fragmentList.add(fragment);
        fragmentTitle.add(title);
    }
}
