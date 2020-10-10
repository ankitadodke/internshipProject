package com.findmyjob.android.modules.employer.postedJobs;

public class ApplicantModel {
    public String id, name, mobile, email;

    public ApplicantModel() {
    }

    public ApplicantModel(String id,String name, String mobile, String email) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }
}
