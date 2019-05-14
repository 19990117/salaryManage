package com.ten.entity;

public class SalaryTransfer {
    private int id;
    private String year;
    private String month;
    private String empNum;
    private String empName;
    private String bankNum;
    private int salary;

    public SalaryTransfer(){}

    public SalaryTransfer(String year, String month, String empNum, String empName, String bankNum, int salary) {
        this.year = year;
        this.month = month;
        this.empNum = empNum;
        this.empName = empName;
        this.bankNum = bankNum;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
