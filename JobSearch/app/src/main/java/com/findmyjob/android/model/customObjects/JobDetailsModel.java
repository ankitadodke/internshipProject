package com.findmyjob.android.model.customObjects;

import java.io.Serializable;

public class JobDetailsModel implements Serializable {
    public String jobLocation, jobTitle, skillSets, payScale, hrName, hrContact, hrEmail, jobInfo, EngReq, Exp, jobTime, qualification;

    JobDetailsModel() {
    }

    public JobDetailsModel(String jobLocation, String jobTitle, String skillSets, String payScale, String qualification, String jobTime, String Exp, String hrName, String jobInfo, String hrContact, String hrEmail, String EngReq) {
        this.jobLocation = jobLocation;
        this.jobTitle = jobTitle;
        this.skillSets = skillSets;
        this.payScale = payScale;
        this.hrName = hrName;
        this.hrContact = hrContact;
        this.hrEmail = hrEmail;
        this.EngReq = EngReq;
        this.jobInfo = jobInfo;
        this.Exp = Exp;
        this.jobTime = jobTime;
        this.qualification = qualification;
    }
}