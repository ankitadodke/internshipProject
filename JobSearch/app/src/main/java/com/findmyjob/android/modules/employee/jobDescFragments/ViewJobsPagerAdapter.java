package com.findmyjob.android.modules.employee.jobDescFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.findmyjob.android.model.customObjects.JobPostModel;

public class ViewJobsPagerAdapter extends FragmentPagerAdapter {
    JobPostModel jobPost;
    public ViewJobsPagerAdapter(@NonNull FragmentManager fm, int behavior, JobPostModel details) {
        super(fm, behavior);
        jobPost = details;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 1) return CompanyDetailsFragment.newInstance(jobPost.companyDetails); else return JobDetailsFragment.newInstance(jobPost.jobDetails,jobPost.id);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 1) return "Company Profile "; else return "Job Description";
    }

    @Override
    public int getCount() {
        return 2;
    }
}