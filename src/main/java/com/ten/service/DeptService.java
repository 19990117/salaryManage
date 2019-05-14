package com.ten.service;

import com.ten.entity.Deduction;
import com.ten.entity.SalaryChange;
import com.ten.entity.Subsidy;
import com.ten.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    @Autowired
    private DeptMapper deptMapper;

    public void insertDeduce(Deduction deduction){
        deptMapper.insertDeduce(deduction);
    }

    public List<Deduction> getDeduceByDept(String deptName){
        return deptMapper.getDeduceByDept(deptName);
    }

    public void insertSubsidy(Subsidy subsidy){
        deptMapper.insertSubsidy(subsidy);
    }

    public List<Subsidy> getSubsidyByDept(String deptName){
        return deptMapper.getSubsidyByDept(deptName);
    }

    public List<SalaryChange> getChangeByDept(String deptName){
        return deptMapper.getChangeByDept(deptName);
    }

    public void delSubsidyById(Integer id){
        deptMapper.delSubsidyById(id);
    }

    public void delDeduceById(Integer id){
        deptMapper.delDeduceById(id);
    }

    public void delChangeById(Integer id){
        deptMapper.delChangeById(id);
    }

    public void updSubsidyById(Integer id,Integer money){
        deptMapper.updSubsidyById(id,money);
    }

    public void updDeduceById(Integer id,Integer money){
        deptMapper.updDeduceById(id,money);
    }

    public void updChangeById(Integer id,Integer money){
        deptMapper.updChangeById(id,money);
    }
}
