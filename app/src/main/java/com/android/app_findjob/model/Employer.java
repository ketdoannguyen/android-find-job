package com.android.app_findjob.model;

public class Employer {
    private int employerID , follower = 0;
    private String name,address,city,activeTime,website,img = null;
    private describe describe ;

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public describe getDescribe() {
        return describe;
    }

    public void setDescribe(describe describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "employerID=" + employerID +
                ", follower=" + follower +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", activeTime='" + activeTime + '\'' +
                ", website='" + website + '\'' +
                ", img='" + img + '\'' +
                ", describe=" + describe +
                '}';
    }
}
