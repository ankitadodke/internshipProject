package com.findmyjob.android.modules.employee.jobDescFragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.CompanyDetailsModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class CompanyDetailsFragment extends Fragment {
    private static final String ARG_COMPANY_DETAILS = "companyDetails";
    FirebaseFirestore fstore;
    StorageReference storageReference;
    ImageView img1;
    FirebaseAuth mAuth;
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
        assert getArguments() != null;
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CompanyDetailsModel companyDetails = (CompanyDetailsModel) getArguments().getSerializable(ARG_COMPANY_DETAILS);
        img1 = rootView.findViewById(R.id.companyLogo);
        TextView txtCompanyName = rootView.findViewById(R.id.txtCompanyName);
        TextView txtCompanyDesc = rootView.findViewById(R.id.txtCompanyInfo);
        TextView txtCompanyEmpNo = rootView.findViewById(R.id.txtCompanyEmpNo);
        TextView txtCompanyPerks = rootView.findViewById(R.id.txtCompanyPerks);
        TextView txtCompanyAddress = rootView.findViewById(R.id.txtCompanyLocation);
        TextView txtCompanyType = rootView.findViewById(R.id.txtCompanyType);

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("companyDetails/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "logo.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img1);
            }
        });
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