package com.findmyjob.android.model.customObjects;

import java.io.Serializable;

public class JobPostModel implements Serializable {
    public JobDetailsModel jobDetails;
    public CompanyDetailsModel companyDetails;
    public String companyId;
    public String id;

    public JobPostModel() {
    }

    public JobPostModel(JobDetailsModel jobDetails, CompanyDetailsModel companyDetails,String companyId,String id) {
        this.jobDetails = jobDetails;
        this.companyDetails = companyDetails;
        this.companyId= companyId;
        this.id = id;
    }
}