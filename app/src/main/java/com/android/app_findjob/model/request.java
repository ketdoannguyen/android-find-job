package com.android.app_findjob.model;


public class request  {

    public request() {
    }

    public request(String req1, String req2, String req3, String req4) {
        this.req1 = req1;
        this.req2 = req2;
        this.req3 = req3;
        this.req4 = req4;
    }

    String  req1, req2, req3,req4 = "";

    public String getReq4() {
        return req4;
    }

    public void setReq4(String req0) {
        this.req4 = req0;
    }

    public String getReq1() {
        return req1;
    }

    public void setReq1(String req1) {
        this.req1 = req1;
    }

    public String getReq2() {
        return req2;
    }

    public void setReq2(String req2) {
        this.req2 = req2;
    }

    public String getReq3() {
        return req3;
    }

    public void setReq3(String req3) {
        this.req3 = req3;
    }
}
