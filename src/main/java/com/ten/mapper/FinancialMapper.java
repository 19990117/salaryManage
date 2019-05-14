package com.ten.mapper;

import com.ten.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FinancialMapper {

    @Select("select * from ledger where empNum=#{empNum}")
    public Ledger getLedgerByEmpNum(String empNum);

    @Select("select * from ledger")
    public List<Ledger> getAllLedger();

    @Select("select * from user where empNum=#{empNum}")
    public User getUserByEmpNum(String empNum);

    @Select("select departmentName from department where departmentId=#{deptNum}")
    public String getDepNameByNum(String deptNum);

    @Select("select * from deduction")
    public List<Deduction> getAllDeduce();

    @Select("select * from subsidy")
    public List<Subsidy> getAllSubsidy();

    @Select("select * from salaryChange")
    public List<SalaryChange> getAllChange();

    @Update("update ledger set base=#{base},payScale=#{payScale},goverSubsidy=#{goverSubsidy},nurRaises=#{nurRaises},teachAge=#{teachAge},onlyChild=#{onlyChild} where empNum=#{empNum}")
    public void updLedger(Ledger ledger);

    @Delete("delete from ledger where empNum=#{empNum}")
    public void delLedgerByEmpNum(String empNum);

    @Insert("insert into salaryManage(year,month,empNum,empName,deptName,num1,num2,num3,sum)" +
            "values(#{year},#{month},#{empNum},#{empName},#{deptName},#{num1},#{num2},#{num3},#{sum})")
    public void insertSalaryManage(SalaryManage salaryManage);

    @Insert("insert into salaryTransfer(year,month,empNum,empName,bankNum,salary)" +
            "values(#{year},#{month},#{empNum},#{empName},#{bankNum},#{salary})")
    public void insertSalaryTransfer(SalaryTransfer salaryTransfer);

    @Select("select * from salaryTransfer")
    public List<SalaryTransfer> getAllTransfer();
}
