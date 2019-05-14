package com.ten.mapper;

import com.ten.entity.Ledger;
import com.ten.entity.SalaryChange;
import com.ten.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonnelMapper {

    @Select("select * from user")
    public List<User> getAllEmp();

    @Select("select * from user where empNum=#{empNum}")
    public User getEmpByEmpNum(String empNum);

    @Select("select departmentId from department where departmentName=#{name}")
    public String getDepNumByName(String name);

    @Update("update user set depNum=#{depNum},position=#{position},staffing=#{staffing} where empNum=#{empNum}")
    public void updateEmp(User user);

    @Delete("delete from user where empNum=#{empNum}")
    public void deleteEmpByEmpNum(String empNum);

    @Insert("insert into user(position,depNum,sex,username,empNum,staffing,age,telNum,IDcard,email,time) " +
            "values(#{position},#{depNum},#{sex},#{username},#{empNum},#{staffing},#{age},#{telNum},#{IDcard},#{email},#{time}) ")
    public void insertEmp(User user);

    @Insert("insert into ledger(year,empNum) values(#{year},#{empNum})")
    public void insertLedger(Ledger ledger);

    @Insert("insert into salaryChange(empNum,year,month,person,beforeMoney,deptName) values(#{empNum},#{year},#{month},#{person},#{beforeMoney},#{deptName})")
    public void insertChange(SalaryChange salaryChange);

    @Select("select allSum from ledger where empNum=#{empNum}")
    public int getAllSumByEmpNum(String empNum);

    @Insert("insert into user_role(userId,roleId) values(#{userId},#{roleId})")
    public void insertRole(@Param("userId") int userId,@Param("roleId") int roleId);
}
