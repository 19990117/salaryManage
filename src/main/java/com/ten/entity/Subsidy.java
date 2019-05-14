package com.ten.entity;

public class Subsidy {
    private int id;
    private String empNum;
    private String deptName;
    private String year;
    private String month;
    private String person;
    private int amount;
    private String remarks;

    public Subsidy(){

    }

    public Subsidy(String empNum, String deptName, String year, String month, String person, int amount, String remarks) {
        this.empNum = empNum;
        this.deptName = deptName;
        this.year = year;
        this.month = month;
        this.person = person;
        this.amount = amount;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
