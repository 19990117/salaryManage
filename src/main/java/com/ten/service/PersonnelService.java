package com.ten.service;

import com.ten.entity.Ledger;
import com.ten.entity.SalaryChange;
import com.ten.entity.User;
import com.ten.mapper.PersonnelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelMapper personnelMapper;

    @Cacheable(cacheNames = "users",key = "#root.methodName")
    public List<User> getAllEmp(){
        List<User> users = personnelMapper.getAllEmp();
        return users;
    }

    public User getEmpByNum(String num){
        User emp = personnelMapper.getEmpByEmpNum(num);
        return emp;
    }

    public String getDepNumByName(String name){
        return personnelMapper.getDepNumByName(name);
    }

    public User updateEmp(User user){
        personnelMapper.updateEmp(user);
        return user;
    }

    public void deleteEmp(String empNum){
        personnelMapper.deleteEmpByEmpNum(empNum);
    }

    public void insertEmp(User user){
        personnelMapper.insertEmp(user);
    }

    public void insertLedger(Ledger ledger){
        personnelMapper.insertLedger(ledger);
    }

    public void insertChange(SalaryChange salaryChange){
        personnelMapper.insertChange(salaryChange);
    }

    public int getAllSum(String empNum){
        int sum = personnelMapper.getAllSumByEmpNum(empNum);
        return sum;
    }

    public void insertRole(int userId,int roleId){
        personnelMapper.insertRole(userId,roleId);
    }

}
