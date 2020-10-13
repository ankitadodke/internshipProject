package com.findmyjob.android.model.customObjects;

import java.io.Serializable;

public class JobDetailsModel implements Serializable {
    public String jobLocation, jobTitle, skillSets, payScale, hrName, hrContact, hrEmail, jobInfo, EngReq, Exp, jobTime, qualification;

    JobDetailsModel() {
    }

    public JobDetailsModel(String jobLocation,String jobTitle,String skillSets, String payScale,String hrName,String hrContact,String hrEmail,String jobInfo,String EngReq,String Exp,String jobTime,String qualification) {
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