package com.ten.service;

import com.ten.entity.SalaryManage;
import com.ten.entity.Subsidy;
import com.ten.entity.User;
import com.ten.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    public String getDepartmentName(User user){
        String name = employeeMapper.getDeparmentNameById(user.getDepNum());
        return name;
    }

    public int getLegder(User user){
        Integer legder = employeeMapper.getLegderByStaffNum(user.getEmpNum());
        if (legder == null)
            return  0;
        else return (int)legder;
    }

    public int getSubsidy(User user){
        List<Subsidy> subsidies = employeeMapper.getSubsidyByStaffNum(user.getEmpNum());
        int sum=0;
        if (!subsidies.isEmpty()){
            for (Subsidy subsidy:subsidies){
                sum += subsidy.getAmount();
            }
        }
        return sum;
    }

    public User updatePwd(User user){
        employeeMapper.updatePwd(user);
        return user;
    }

    public User updateBank(User user){
        employeeMapper.updateBank(user);
        return user;
    }

    public List<SalaryManage> getAllSalaryByNum(String empNum){
        return employeeMapper.getAllSalaryByNum(empNum);
    }
}
