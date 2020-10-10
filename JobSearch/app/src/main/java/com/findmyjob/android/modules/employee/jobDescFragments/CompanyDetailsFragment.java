package com.findmyjob.android.modules.employee.jobDescFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.CompanyDetailsModel;

public class CompanyDetailsFragment extends Fragment {
    private static final String ARG_COMPANY_DETAILS = "companyDetails";

    public static CompanyDetailsFragment newInstance(CompanyDetailsModel companyDetails) {
        CompanyDetailsFragment fragment = new CompanyDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_COMPANY_DETAILS, companyDetails);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.company_details_frag, container, false);
        CompanyDetailsModel companyDetails = (CompanyDetailsModel) getArguments().getSerializable(ARG_COMPANY_DETAILS);
        TextView txtCompanyName = rootView.findViewById(R.id.txtCompanyName);
        TextView txtCompanyDesc = rootView.findViewById(R.id.txtCompanyInfo);
        TextView txtCompanyEmpNo = rootView.findViewById(R.id.txtCompanyEmpNo);
        TextView txtCompanyPerks = rootView.findViewById(R.id.txtCompanyPerks);
        TextView txtCompanyAddress = rootView.findViewById(R.id.txtCompanyLocation);
        TextView txtCompanyType = rootView.findViewById(R.id.txtCompanyType);

        if (companyDetails != null) {
            txtCompanyName.setText(companyDetails.name);
            txtCompanyType.setText(companyDetails.type);
            txtCompanyEmpNo.setText(companyDetails.employeeCount);
            txtCompanyPerks.setText(companyDetails.perks);
            txtCompanyAddress.setText(companyDetails.location);
            txtCompanyDesc.setText(companyDetails.description);
        }
        return rootView;
    }
}