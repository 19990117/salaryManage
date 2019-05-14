package com.ten.controller;

import com.alibaba.fastjson.JSON;
import com.ten.aspect.OperationLog;
import com.ten.entity.Ledger;
import com.ten.entity.SalaryChange;
import com.ten.entity.User;
import com.ten.service.FinancialService;
import com.ten.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/*
* 人事部权限
* 人事部功能为增添人事时，自动为台账生成模板
* 人事部门只负责人事的调动，所以在人事管理上只能修改其三项
* */
@RestController
@RequestMapping(value = "/personnel")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private FinancialService financialService;

    @RequestMapping(value = "/liststaff")
    public List<Object> getAllEmp(){
        Map<String ,Object > resMap = new LinkedHashMap<>();
        List<User> users = personnelService.getAllEmp();
        int i = 1;
        List<Object> resList = new ArrayList<>();
        for (User user:users){
            Map<String ,Object > userMap = new LinkedHashMap<>();
            String deptName = financialService.getDeptNameByNum(user.getDepNum());
            userMap.put("name",user.getUsername());
            userMap.put("number",user.getEmpNum());
            userMap.put("sex",user.getSex());
            userMap.put("age",user.getAge());
            userMap.put("idcard",user.getIDcard());
            userMap.put("phone",user.getTelNum());
            userMap.put("email",user.getEmail());
            userMap.put("department",deptName);
            userMap.put("position",user.getPosition());
            userMap.put("time",user.getTime());
            userMap.put("bankcard",user.getBankNum());
            //resMap.put("result" + i++,userMap);
            resList.add(userMap);
        }
        //String  param= JSON.toJSONString(resMap);
        return resList;
    }

    //人事部只能修改员工的这三项
    @OperationLog(value = "员工修改")
    @PutMapping(value = "/updEmp")
    public User updateEmp(@RequestParam("depName") String depName,
                          @RequestParam("position") String position,
                          @RequestParam("empNum") String empNum){
        User user = personnelService.getEmpByNum(empNum);
        String depNum = personnelService.getDepNumByName(depName);
        if (!user.getPosition().equals(position)){
            user.setPosition(position);
        }
        if (!user.getDepNum().equals(depNum)){
            user.setDepNum(depNum);
        }
        //当人事部修改这些属性时，默认为职位发生变动，生成职位变动数据
        SalaryChange salaryChange = new SalaryChange();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String s = df.format(new Date());
        String year = s.substring(0,4);
        String month = s.substring(5,7);
        int before = personnelService.getAllSum(empNum);
        salaryChange.setBeforeMoney(before);
        salaryChange.setYear(year);
        salaryChange.setMonth(month);
        salaryChange.setEmpNum(empNum);
        salaryChange.setDeptName(depName);
        salaryChange.setPerson(user.getUsername());
        personnelService.insertChange(salaryChange);

        personnelService.updateEmp(user);
        return user;
    }

    @OperationLog(value = "删除员工")
    @DeleteMapping(value = "/delEmp")
    public String delEmp(@RequestParam("empNum") String empNum){
        financialService.delLedgerByEmpNum(empNum);
        personnelService.deleteEmp(empNum);
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }


    @OperationLog(value = "添加员工")
    @PostMapping(value = "/insertEmp")
    public String insertEmp(@RequestParam("position") String position,
                          @RequestParam("depName") String depName,
                          @RequestParam("sex") String sex,
                          @RequestParam("username") String username,
                          @RequestParam("empNum") String empNum,
                          @RequestParam("staffing") String staffing,
                          @RequestParam("age") String age,
                          @RequestParam("telNum") String telNum,
                          @RequestParam("IDcard") String IDcard,
                          @RequestParam("email") String email,
                          @RequestParam("time") String time){
        User emp = personnelService.getEmpByNum(empNum);
        HashMap<String,Object> info = new HashMap<>();
        if (emp == null){
            User user = new User();
            String depNum = personnelService.getDepNumByName(depName);
            user.setPosition(position);
            user.setStaffing(staffing);
            user.setSex(sex);
            user.setDepNum(depNum);
            user.setUsername(username);
            user.setEmpNum(empNum);
            user.setIDcard(IDcard);
            user.setAge(age);
            user.setTelNum(telNum);
            user.setEmail(email);
            user.setTime(time);
            personnelService.insertEmp(user);
            User user1 = financialService.getUserByEmpNum(empNum);
            //增添人事时，根据人员的部门，为人员添加相应的权限
            int i = user1.getId();
            if ("人事部".equals(depName)){
                personnelService.insertRole(i,3);
            }else if ("计财处".equals(depName)){
                personnelService.insertRole(i,4);
            }else if ("部门财务".equals(depName)){
                personnelService.insertRole(i,5);
            }else {
                personnelService.insertRole(i,2);
            }
            //增添台账模板
            Ledger ledger = new Ledger();
            String year = time.substring(0,4);
            ledger.setYear(year);
            ledger.setEmpNum(empNum);
            personnelService.insertLedger(ledger);
            info.put("info","success");
            String  param= JSON.toJSONString(info);
            return param;
        }else{
            info.put("info","empNum have already exits");
            String  param= JSON.toJSONString(info);
            return param;
        }
    }

}
