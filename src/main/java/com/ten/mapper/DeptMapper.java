package com.ten.mapper;

import com.ten.entity.Deduction;
import com.ten.entity.SalaryChange;
import com.ten.entity.Subsidy;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    @Insert("insert deduction(empNum,deptName,year,month,person,amount,remarks) " +
            "values(#{empNum},#{deptName},#{year},#{month},#{person},#{amount},#{remarks})")
    public void insertDeduce(Deduction deduction);

    @Select("select * from deduction where deptName=#{deptName}")
    public List<Deduction> getDeduceByDept(String deptName);

    @Insert("insert subsidy(empNum,deptName,year,month,person,amount,remarks) " +
            "values(#{empNum},#{deptName},#{year},#{month},#{person},#{amount},#{remarks})")
    public void insertSubsidy(Subsidy subsidy);

    @Select("select * from subsidy where deptName=#{deptName}")
    public List<Subsidy> getSubsidyByDept(String deptName);

    @Select("select * from salaryChange where deptName=#{deptName}")
    public List<SalaryChange> getChangeByDept(String deptName);

    @Delete("delete from deduction where id=#{id}")
    public void delDeduceById(Integer id);

    @Delete("delete from subsidy where id=#{id}")
    public void delSubsidyById(Integer id);

    @Delete("delete from salaryChange where id=#{id}")
    public void delChangeById(Integer id);

    @Update("update deduction set amount=#{money} where id=#{id}")
    public void updDeduceById(Integer id,Integer money);

    @Update("update subsidy set amount=#{money} where id=#{id}")
    public void updSubsidyById(Integer id,Integer money);

    @Update("update salaryChange set after=#{money} where id=#{id}")
    public void updChangeById(Integer id,Integer money);
}
