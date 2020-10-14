package com.findmyjob.android.model.customObjects;

import java.io.Serializable;

public class CompanyDetailsModel implements Serializable {
    public String name,description,type, perks,location,employeeCount;

    public CompanyDetailsModel() {
    }

    public CompanyDetailsModel(String name, String description, String type, String perks, String location, String employeeCount) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.perks = perks;
        this.location = location;
        this.employeeCount = employeeCount;

    }
}