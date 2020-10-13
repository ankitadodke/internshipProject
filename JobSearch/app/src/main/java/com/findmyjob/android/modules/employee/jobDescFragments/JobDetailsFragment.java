package com.findmyjob.android.modules.employee.jobDescFragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.findmyjob.android.R;
import com.findmyjob.android.model.customObjects.JobDetailsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JobDetailsFragment extends Fragment {
    private static final String ARG_JOB_DETAILS = "jobDetails";
    private static final String ARG_ID = "documentId";

    private final int REQUEST_CODE_PHONE_CALL = 1000;
    Button btnCallNow, btnApplyNow;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static JobDetailsFragment newInstance(JobDetailsModel jobDetails, String id) {
        JobDetailsFragment fragment = new JobDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_JOB_DETAILS, jobDetails);
        bundle.putString(ARG_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.job_details_frag, container, false);
        final JobDetailsModel jobDetails = (JobDetailsModel) getArguments().getSerializable(ARG_JOB_DETAILS);
        final String jobId = getArguments().getString(ARG_ID);

        final Context context = container.getContext();
        TextView txtJobTitle = rootView.findViewById(R.id.txtJobTitle);
        TextView txtJobLocation = rootView.findViewById(R.id.txtJobLocation);
        TextView txtJobPayScale = rootView.findViewById(R.id.txtPayScale);
        TextView txtJobQualification = rootView.findViewById(R.id.textQualification);
        TextView txtJobEnglish = rootView.findViewById(R.id.txtEnglish);
        TextView txtJobSkills = rootView.findViewById(R.id.txtSkills);
        TextView txtJobInfo = rootView.findViewById(R.id.txtJobInfo);
        TextView txtJobExp = rootView.findViewById(R.id.txtJobExp);
        TextView txtJobTiming = rootView.findViewById(R.id.txtJobTiming);
        TextView txtJobContactPerson = rootView.findViewById(R.id.txtContactPerson);
        TextView txtJobContactPersonEmail = rootView.findViewById(R.id.txtContactPersonEmail);
        final TextView txtJobContactPersonPhone = rootView.findViewById(R.id.txtContactPersonPhone);
        btnCallNow = rootView.findViewById(R.id.btnCall);
        btnApplyNow = rootView.findViewById(R.id.btnApplyNow);

        if (jobDetails != null) {
            txtJobTitle.setText(jobDetails.jobTitle);
            txtJobLocation.setText(jobDetails.jobLocation);
            txtJobPayScale.setText(jobDetails.payScale);
            txtJobQualification.setText(jobDetails.jobTitle);
            txtJobSkills.setText(jobDetails.skillSets);
            txtJobContactPerson.setText(jobDetails.hrName);
            txtJobContactPersonEmail.setText(jobDetails.hrEmail);
            txtJobContactPersonPhone.setText(jobDetails.hrContact);
            txtJobEnglish.setText(jobDetails.EngReq);
            txtJobInfo.setText(jobDetails.jobInfo);
            txtJobTiming.setText(jobDetails.jobTime);
            txtJobExp.setText(jobDetails.Exp);


        }

        btnApplyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                String userId = firebaseAuth.getCurrentUser().getUid();
                map.put("time", Calendar.getInstance().getTimeInMillis() + "");
                map.put("id", userId);
                map.put("mobile", firebaseAuth.getCurrentUser().getPhoneNumber());
                fStore.collection("jobs").document(jobId).collection("applicants").document(userId).set(map);
                Toast.makeText(context, "Applied for job successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnCallNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + jobDetails.hrContact));
                    startActivity(callIntent);
                } else
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_PHONE_CALL);
            }
        });


        return rootView;
    }
}