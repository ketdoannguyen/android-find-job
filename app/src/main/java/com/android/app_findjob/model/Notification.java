package com.android.app_findjob.model;

public class Notification {

    private int id = 0;
    private String title ,detail,time,type,status = "";

    public Notification() {
    }

    public Notification(int id, String title, String detail, String time, String type, String status) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.time = time;
        this.type = type;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
