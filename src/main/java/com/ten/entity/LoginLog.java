package com.ten.entity;

public class LoginLog {
    private String id;
    private String userid;
    private String time;
    private String status;
    private String ip;

    public LoginLog(){}

    public LoginLog(String id, String userid, String time, String status, String ip) {
        this.id = id;
        this.userid = userid;
        this.time = time;
        this.status = status;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
