package com.ten.controller;

import com.ten.aspect.OperationLog;
import com.ten.entity.*;
import com.ten.service.EmployeeService;
import com.ten.service.FinancialService;
import com.ten.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.*;

/*
* 计财处权限
* 计财处的功能为查看各部门填写的扣款补贴信息
* 当人事新加人员时，会自动生成一个台账模板，由计财处人员填写完整
* */
@RestController
@RequestMapping(value = "/financial")
public class FinancialController {
    @Autowired
    private FinancialService financialService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/listparameter")
    public List<Object> getAllLedger(){
        Map<String ,Object > resMap = new LinkedHashMap<>();
        int i = 1;
        List<Object> resList = new ArrayList<>();
        resMap.put("res_code","000");
        resMap.put("res_msg","success");
        List<Ledger> ledgers = financialService.getAllLedger();
        for (Ledger ledger:ledgers){
            Map<String ,Object > ledgerMap = new LinkedHashMap<>();
            User user = financialService.getUserByEmpNum(ledger.getEmpNum());
            if (user != null){
                String deptName = financialService.getDeptNameByNum(user.getDepNum());
                ledgerMap.put("year",ledger.getYear());
                ledgerMap.put("department",deptName);
                ledgerMap.put("kind",user.getPosition());
                ledgerMap.put("number",user.getEmpNum());
                ledgerMap.put("name",user.getUsername());
                ledgerMap.put("postsalary",ledger.getBase());
                ledgerMap.put("paywages",ledger.getPayScale());
                ledgerMap.put("governmentsubsidies",ledger.getGoverSubsidy());
                ledgerMap.put("teachersraisetheirsalaries",ledger.getNurRaises());
                ledgerMap.put("childreallowance",ledger.getTeachAge());
                ledgerMap.put("onlychildbonus",ledger.getOnlyChild());
            }else{
                continue;
            }
            //resMap.put("result" + i++,ledgerMap);
            resList.add(ledgerMap);
        }

        //String  param= JSON.toJSONString(resMap);
        return resList;
    }

    @OperationLog(value = "修改台账")
    @PutMapping(value = "/updLedger")
    public String updLedger(@RequestParam("empNum") String empNum,
                                        @RequestParam("payScale") int payScale,
                                        @RequestParam("base") int base,
                                        @RequestParam("goverSubsidy") int goverSubsidy,
                                        @RequestParam("nurRaises") int nurRaises,
                                        @RequestParam("teachAge") int teachAge,
                                        @RequestParam("onlyChild") int onlyChild){
        Ledger ledger = financialService.getLedgerByEmpNum(empNum);
        HashMap<String,Object> info = new HashMap<>();
        if (ledger == null){
            info.put("info","error,there is no such people");
            String  param= JSON.toJSONString(info);
            return param;
        }
        int sum = base+payScale+goverSubsidy+nurRaises+teachAge+onlyChild;
        ledger.setBase(base);
        ledger.setGoverSubsidy(goverSubsidy);
        ledger.setNurRaises(nurRaises);
        ledger.setOnlyChild(onlyChild);
        ledger.setPayScale(payScale);
        ledger.setTeachAge(teachAge);
        ledger.setAllSum(sum);
        financialService.updLedger(ledger);
        info.put("info","success");
        String  param= JSON.toJSONString(info);
        return param;
    }

    /*台账不能删除，只能由人事部处理了
    @RequestMapping(value = "/delLedger")
    public void delLedger(@RequestParam("empNum") String empNum){
        financialService.delLedgerByEmpNum(empNum);
    }*/

    @RequestMapping(value = "/listdeductions")
    public List<Object> getAllDeduce(){
        Map<String ,Object > resMap = new LinkedHashMap<>();
        int i = 1;
        List<Object> res = new ArrayList<>();
        resMap.put("res_code","000");
        resMap.put("res_msg","success");
        List<Deduction> deductions = financialService.getAllDeduce();
        for (Deduction deduction:deductions){
            User user = financialService.getUserByEmpNum(deduction.getEmpNum());
            if (user == null){
                continue;
            }
            String name = user.getUsername();
            Map<String ,Object > deduceMap = new LinkedHashMap<>();
            deduceMap.put("year",deduction.getYear());
            deduceMap.put("month",deduction.getMonth());
            deduceMap.put("department",deduction.getDeptName());
            deduceMap.put("number",deduction.getEmpNum());
            deduceMap.put("name",name);
            deduceMap.put("money",deduction.getAmount());
            deduceMap.put("person",deduction.getPerson());

            //resMap.put("result" + i++,deduceMap);
            res.add(deduceMap);
        }
        //String  param= JSON.toJSONString(resMap);
        return res;
    }

    @RequestMapping(value = "/listsubsidies")
    public List<Object> getAllSubsidy(){
        Map<String ,Object > resMap = new LinkedHashMap<>();
        int i =1;
        List<Object> resList = new ArrayList<>();
        resMap.put("res_code","000");
        resMap.put("res_msg","success");
        List<Subsidy> subsidies = financialService.getAllSubsidy();
        for (Subsidy subsidy:subsidies){
            Map<String ,Object > subsidyMap = new LinkedHashMap<>();
            User user = financialService.getUserByEmpNum(subsidy.getEmpNum());
            if (user == null){
                continue;
            }
            subsidyMap.put("year",subsidy.getYear());
            subsidyMap.put("month",subsidy.getMonth());
            subsidyMap.put("department",subsidy.getDeptName());
            subsidyMap.put("number",subsidy.getEmpNum());
            subsidyMap.put("name",user.getUsername());
            subsidyMap.put("money",subsidy.getAmount());
            subsidyMap.put("person",subsidy.getPerson());

            //resMap.put("results" + i++,subsidyMap);
            resList.add(subsidyMap);
        }
        //String  param= JSON.toJSONString(resMap);
        return resList;
    }

    @RequestMapping(value = "/listwagechange")
    public List<Object> getAllChange(){
        Map<String ,Object > resMap = new LinkedHashMap<>();
        int i=1;
        List<Object> resList = new ArrayList<>();
        resMap.put("res_code","000");
        resMap.put("res_msg","success");
        List<SalaryChange> changes = financialService.getAllChange();
        for (SalaryChange change:changes){
            Map<String ,Object > changeMap = new LinkedHashMap<>();
            changeMap.put("year",change.getYear());
            changeMap.put("month",change.getMonth());
            changeMap.put("department",change.getDeptName());
            changeMap.put("number",change.getEmpNum());
            changeMap.put("name",change.getPerson());
            changeMap.put("beforemoney",change.getBeforeMoney());
            changeMap.put("aftermoney",change.getAfter());

            //resMap.put("result" + i++,changeMap);
            resList.add(changeMap);
        }
        //String  param= JSON.toJSONString(resMap);
        return resList;
    }

    @RequestMapping(value = "/listtransfer")
    public List<Object> getAllTransfer(){
        Map<String ,Object > resMap = new LinkedHashMap<>();
        int i=1;
        List<Object> resList = new ArrayList<>();
        List<SalaryTransfer> transfers = financialService.getAllTransfer();
        for (SalaryTransfer transfer:transfers){
            Map<String ,Object > transferMap = new LinkedHashMap<>();
            transferMap.put("year",transfer.getYear());
            transferMap.put("month",transfer.getMonth());
            transferMap.put("number",transfer.getEmpNum());
            transferMap.put("name",transfer.getEmpName());
            transferMap.put("banknumber",transfer.getBankNum());
            transferMap.put("money",transfer.getSalary());

            //resMap.put("result" + i++,transferMap);
            resList.add(transferMap);
        }
        //String  param= JSON.toJSONString(resMap);
        return resList;
    }



    //每月最后一天自动生成本月账单和银行转账单
    private void produceSalary(){
        List<User> users = userService.getAllUsers();
        for (User user:users){
            String deptName = employeeService.getDepartmentName(user);
            int ledger = employeeService.getLegder(user);
            int subsidy = employeeService.getSubsidy(user);
            int sum = ledger + subsidy;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String s = df.format(new Date());
            String year = s.substring(0,4);
            String month = s.substring(5,7);
            SalaryManage salaryManage = new SalaryManage(year,month,user.getEmpNum(),user.getUsername(),deptName,ledger,subsidy,0,sum);
            SalaryTransfer salaryTransfer = new SalaryTransfer(year,month,user.getEmpNum(),user.getUsername(),user.getBankNum(),sum);
            financialService.insertSalaryManage(salaryManage);
            financialService.insertSalaryTransfer(salaryTransfer);
        }
    }

}
