package com.android.app_findjob.model;

public class EmployerFollow {
    private int employerID , id = 0;

    public EmployerFollow() {
    }
    public EmployerFollow(int employerID, int id) {
        this.employerID = employerID;
        this.id = id;
    }

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
