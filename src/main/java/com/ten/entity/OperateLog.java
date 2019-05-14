package com.ten.entity;

public class OperateLog {
private String id;
    private String type;
    private String userid;
    private String username;
    private String ip;
    private String description;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    @Override
    public String toString() {
        return "OperateLog{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                ", desc='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
