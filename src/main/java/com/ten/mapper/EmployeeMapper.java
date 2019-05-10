package com.ten.mapper;

import com.ten.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    @Select("select departmentName from department where departmentId = #{deptId}")
    String getDeparmentNameById(String deptId);

    @Select("select allSum from ledger where staffNumber = #{staffNum}")
    public int getLegderByStaffNum(String staffNum);

    @Select("select allSubsidy from subsidy where staffNum = #{staffNum}")
    public int getSubsidyByStaffNum(String staffNum);

    @Update("update user set password = #{password} where username = #{username}")
    public void updatePwd(User user);

    @Update("update user set IDcard=#{IDcard},bankName=#{bankName},bankNum=#{bankNum} where username=#{username}")
    public void updateBank(User user);

}
