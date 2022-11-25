package com.singh.myshop.LoginModelG;

public class LoginModel {

    String name,mobileNo,date,fcmToken;

    public LoginModel(String name, String mobileNo, String date,String fcmToken) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.date = date;
        this.fcmToken = fcmToken;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFCMToken() {
        return fcmToken;
    }

    public void setFCMToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
