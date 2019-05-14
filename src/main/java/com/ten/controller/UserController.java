package com.ten.controller;


import com.ten.aspect.OperationLog;
import com.ten.entity.SalaryManage;
import com.ten.entity.User;
import com.ten.service.EmployeeService;
import com.ten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import java.util.*;

/*
* 普通员工界面
* 查看信息，绑定银行卡，修改密码
* */
@RestController
@RequestMapping(value = "/user")
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

    @RequestMapping(value = "/firstwage")
    public List<Object> getUserFirstPage(){

        //获取当前user对象
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        //User user = userService.getAllUsers().get(0);//测试用数据库的用户
        Map<String ,Object > resMap = new LinkedHashMap<>();

        List<Object> resList = new ArrayList<>();

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
            resMap.put("number",user.getEmpNum());
            resMap.put("name",user.getUsername());
            resMap.put("sex",user.getSex());
            resMap.put("age",user.getAge());
            resMap.put("department",deptName);
            resMap.put("position",user.getPosition());
            resMap.put("sum1",ledger);
            resMap.put("sum2",subsidy);
            resMap.put("sum3","0");
            resMap.put("sum",sum);
        }
        resList.add(resMap);

        return resList;
    }

    @OperationLog(value = "修改密码")
    @PutMapping(value = "/updatePwd")
    public String updatePassword(@RequestParam("password") String password){
        //获取当前user对象
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        //User user = userService.getAllUsers().get(0);//测试用数据库的用户

        user.setPassword(password);
        employeeService.updatePwd(user);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @OperationLog(value = "绑定银行卡")
    @PutMapping(value = "/bindBank")
    public String bindBank(@RequestParam("IDcard") String IDcard,
                         @RequestParam("bankName") String bankName,
                         @RequestParam("bankNum") String bankNum){
        //获取当前user对象
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        //User user = userService.getAllUsers().get(0);//测试用数据库的用户

        user.setBankName(bankName);
        user.setBankNum(bankNum);
        user.setIDcard(IDcard);
        employeeService.updateBank(user);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @RequestMapping(value = "/listwage")
    public List<Object> getAllSalaryManage(){
        //获取当前user对象
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        //System.out.println(user.getEmpNum());

        Map<String ,Object > resMap = new LinkedHashMap<>();
        List<Object> resList = new ArrayList<>();
        int i=1;
        List<SalaryManage> manages = employeeService.getAllSalaryByNum(user.getEmpNum());
        for (SalaryManage manage:manages){
            Map<String ,Object > manageMap = new LinkedHashMap<>();
            manageMap.put("date",manage.getYear()+manage.getMonth());
            manageMap.put("number",manage.getEmpNum());
            manageMap.put("name",manage.getEmpName());
            manageMap.put("department",manage.getDeptName());
            manageMap.put("sum1",manage.getNum1());
            manageMap.put("sum2",manage.getNum2());
            manageMap.put("sum3",manage.getNum3());
            manageMap.put("sum",manage.getSum());

            resList.add(manageMap);
            //resMap.put("result" + i++,manageMap);
        }
        //String  param= JSON.toJSONString(resMap);
        return resList;
    }

}
