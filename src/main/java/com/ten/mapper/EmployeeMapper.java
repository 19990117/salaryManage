package com.ten.mapper;

import com.ten.entity.SalaryManage;
import com.ten.entity.Subsidy;
import com.ten.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("select departmentName from department where departmentId = #{deptId}")
    String getDeparmentNameById(String deptId);

    @Select("select allSum from ledger where empNum = #{staffNum}")
    public Integer getLegderByStaffNum(String staffNum);

    @Select("select * from subsidy where empNum = #{staffNum}")
    public List<Subsidy> getSubsidyByStaffNum(String staffNum);

    @Update("update user set password = #{password} where username = #{username}")
    public void updatePwd(User user);

    @Update("update user set IDcard=#{IDcard},bankName=#{bankName},bankNum=#{bankNum} where username=#{username}")
    public void updateBank(User user);

    @Select("select * from salaryManage where empNum=#{empNum}")
    public List<SalaryManage> getAllSalaryByNum(String empNum);

}
