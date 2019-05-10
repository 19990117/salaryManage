package com.ten.controller;


import com.ten.entity.User;
import com.ten.service.EmployeeService;
import com.ten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    /*
    *   @RequiresPermissions("user")表示需要获取user权限才能调用接口
    * */
    //@RequiresPermissions("user")
    @RequestMapping(value = "/allusers")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    @RequestMapping(value = "/listWage")
    public Map<String,Object> getUserFirstPage(){

        //获取当前user对象
        //org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        //User user = (User)subject.getPrincipal();
        User user = userService.getAllUsers().get(0);//测试用数据库的用户
        Map<String ,Object > resMap = new LinkedHashMap<>();

        if (user == null){
            resMap.put("res_code","000");
            resMap.put("res_msg","error");
        }else {
            resMap.put("res_code","000");
            resMap.put("res_msg","success");

            String deptName = employeeService.getDepartmentName(user);
            int ledger = employeeService.getLegder(user);
            int subsidy = employeeService.getSubsidy(user);
            int sum = ledger + subsidy;
            resMap.put("name",user.getUsername());
            resMap.put("sex",user.getSex());
            resMap.put("age",user.getAge());
            resMap.put("empNum",user.getEmpNum());
            resMap.put("position",user.getPosition());
            resMap.put("departmentName",deptName);
            resMap.put("ledger",ledger);
            resMap.put("subsidy",subsidy);
            resMap.put("dependence","0");
            resMap.put("sum",sum);
        }

        return resMap;
    }

    @RequestMapping(value = "/updatePwd")
    public void updatePassword(@RequestParam("password") String password){
        //获取当前user对象
        //org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        //User user = (User)subject.getPrincipal();
        User user = userService.getAllUsers().get(0);//测试用数据库的用户

        user.setPassword(password);
        employeeService.updatePwd(user);
    }

    @RequestMapping(value = "/bindBank")
    public void bindBank(@RequestParam("IDcard") String IDcard,
                         @RequestParam("bankName") String bankName,
                         @RequestParam("bankNum") String bankNum){
        //获取当前user对象
        //org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        //User user = (User)subject.getPrincipal();
        User user = userService.getAllUsers().get(0);//测试用数据库的用户

        user.setBankName(bankName);
        user.setBankNum(bankNum);
        user.setIDcard(IDcard);
        employeeService.updateBank(user);
    }


}
