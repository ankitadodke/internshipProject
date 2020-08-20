package com.findmyjob.android.model.customObjects;

public class UserModel {
    String mobileNumber;
    String role;

    public UserModel() {
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserModel(String mobileNumber, String role) {
        this.mobileNumber = mobileNumber;
        this.role = role;
    }
}
