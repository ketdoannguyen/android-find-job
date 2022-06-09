package com.android.app_findjob.model;

public class JobFollow {
    private int jobID , id = 0;

    public JobFollow() {
    }
    public JobFollow(int jobID, int id) {
        this.jobID = jobID;
        this.id = id;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
