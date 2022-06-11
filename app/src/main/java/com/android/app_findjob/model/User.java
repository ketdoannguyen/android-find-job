package com.android.app_findjob.model;


public class User{
    private String id ,nameAccount,email,pass,fullname,birthday,phone,address = "";

    public User() {
    }

    public User(String id, String nameAccount, String email, String pass) {
        this.id = id;
        this.nameAccount = nameAccount;
        this.email = email;
        this.pass = pass;
    }

    public User(String id, String nameAccount, String email, String pass, String fullname, String birthday, String phone, String address) {
        this.id = id;
        this.nameAccount = nameAccount;
        this.email = email;
        this.pass = pass;
        this.fullname = fullname;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}