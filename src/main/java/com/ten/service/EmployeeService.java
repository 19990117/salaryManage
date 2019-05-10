package com.ten.service;

import com.ten.entity.User;
import com.ten.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    public String getDepartmentName(User user){
        String name = employeeMapper.getDeparmentNameById(user.getDepNum());
        return name;
    }

    public int getLegder(User user){
        int legder = employeeMapper.getLegderByStaffNum(user.getEmpNum());
        return legder;
    }

    public int getSubsidy(User user){
        int subsidy = employeeMapper.getSubsidyByStaffNum(user.getEmpNum());
        return subsidy;
    }

    public User updatePwd(User user){
        employeeMapper.updatePwd(user);
        return user;
    }

    public User updateBank(User user){
        employeeMapper.updateBank(user);
        return user;
    }
}
