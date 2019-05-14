package com.ten.entity;

public class SalaryManage {
    private int id;
    private String year;
    private String month;
    private String empNum;
    private String empName;
    private String deptName;
    private int num1;
    private int num2;
    private int num3;
    private int sum;

    public SalaryManage(){}

    public SalaryManage(String year, String month, String empNum, String empName, String deptName, int num1, int num2, int num3, int sum) {
        this.year = year;
        this.month = month;
        this.empNum = empNum;
        this.empName = empName;
        this.deptName = deptName;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.sum = sum;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
