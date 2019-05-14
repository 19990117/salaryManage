package com.ten.controller;

import com.alibaba.fastjson.JSON;
import com.ten.aspect.OperationLog;
import com.ten.entity.Deduction;
import com.ten.entity.SalaryChange;
import com.ten.entity.Subsidy;
import com.ten.entity.User;
import com.ten.service.DeptService;
import com.ten.service.FinancialService;
import com.ten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/*
* 部门财务权限
* 部门财务根据登录的部门财务管理员的部门号提供相应部门补贴扣款查询
* 部门财务还应有填写补贴扣款等功能
* */
@RestController
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;

    @Autowired
    private FinancialService financialService;

    @OperationLog(value = "部门填写扣款清单")
    @PostMapping(value = "/insertDeduce")
    public String insertDeduce(@RequestParam("empNum") String empNum,
                             @RequestParam("deptName") String deptName,
                             @RequestParam("year") String year,
                             @RequestParam("month") String month,
                             @RequestParam("person") String person,
                             @RequestParam("amount") Integer amount,
                             @RequestParam("remarks") String remarks){
        Deduction deduction = new Deduction(empNum,deptName,year,month,person,amount,remarks);
        deptService.insertDeduce(deduction);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @OperationLog(value = "部门填写补贴清单")
    @PostMapping(value = "/insertSubsidy")
    public String insertSubsidy(@RequestParam("empNum") String empNum,
                             @RequestParam("deptName") String deptName,
                             @RequestParam("year") String year,
                             @RequestParam("month") String month,
                             @RequestParam("person") String person,
                             @RequestParam("amount") Integer amount,
                             @RequestParam("remarks") String remarks){
        Subsidy subsidy = new Subsidy(empNum,deptName,year,month,person,amount,remarks);
        deptService.insertSubsidy(subsidy);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @RequestMapping(value = "/getDeduce")
    public List<Deduction> getAllDeduceByDeptName(){
        //获取当前user对象
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        //User user = userService.getAllUsers().get(0);//测试用数据库的用户

        String deptName = financialService.getDeptNameByNum(user.getDepNum());
        return deptService.getDeduceByDept(deptName);
    }

    @RequestMapping(value = "/getSubsidy")
    public List<Subsidy> getAllSubsidyByDeptName(){
        //获取当前user对象
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        //User user = userService.getAllUsers().get(0);//测试用数据库的用户

        String deptName = financialService.getDeptNameByNum(user.getDepNum());
        return deptService.getSubsidyByDept(deptName);
    }

    @RequestMapping(value = "/getChange")
    public List<SalaryChange> getAllByDeptName(){
        //获取当前user对象
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        String deptName = financialService.getDeptNameByNum(user.getDepNum());
        return deptService.getChangeByDept(deptName);
    }

    @OperationLog(value = "部门删除扣款清单")
    @DeleteMapping(value = "/delDeduce")
    public String delDeduce(@RequestParam("id") int id){
        deptService.delDeduceById(id);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @OperationLog(value = "部门删除补贴清单")
    @DeleteMapping(value = "/delSubsidy")
    public String delSubsidy(@RequestParam("id") int id){
        deptService.delSubsidyById(id);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @OperationLog(value = "部门删除工资变动单")
    @DeleteMapping(value = "/delChange")
    public String delChange(@RequestParam("id") int id){
        deptService.delChangeById(id);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @OperationLog(value = "部门更新扣款清单")
    @PutMapping(value = "/updDeduce")
    public String updDeduce(@RequestParam("id") int id,
                          @RequestParam("money") int money){
        deptService.updDeduceById(id,money);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @OperationLog(value = "部门更新补贴清单")
    @PutMapping(value = "/updSubsidy")
    public String updSubsidy(@RequestParam("id") int id,
                           @RequestParam("money") int money){
        deptService.updSubsidyById(id,money);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    @OperationLog(value = "部门更新工资变动清单")
    @PutMapping(value = "/updChange")
    public String updChange(@RequestParam("id") int id,
                           @RequestParam("money") int money){
        deptService.updChangeById(id,money);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

}
