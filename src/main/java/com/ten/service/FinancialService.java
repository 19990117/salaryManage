package com.ten.service;

import com.ten.entity.*;
import com.ten.mapper.FinancialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialService {
    @Autowired
    private FinancialMapper financialMapper;

    public Ledger getLedgerByEmpNum(String empNum){
        Ledger ledger = financialMapper.getLedgerByEmpNum(empNum);
        return ledger;
    }

    @Cacheable(cacheNames = "ledgers",key = "#root.methodName")
    public List<Ledger> getAllLedger(){
        return financialMapper.getAllLedger();
    }

    public User getUserByEmpNum(String empNum){
        return financialMapper.getUserByEmpNum(empNum);
    }

    public String getDeptNameByNum(String deptNum){
        return financialMapper.getDepNameByNum(deptNum);
    }

    @Cacheable(cacheNames = "deductions",key = "#root.methodName")
    public List<Deduction> getAllDeduce(){
        return financialMapper.getAllDeduce();
    }

    @Cacheable(cacheNames = "subsidies",key = "#root.methodName")
    public List<Subsidy> getAllSubsidy(){
        return financialMapper.getAllSubsidy();
    }

    @Cacheable(cacheNames = "salaryChanges",key = "#root.methodName")
    public List<SalaryChange> getAllChange(){
        return financialMapper.getAllChange();
    }

    public void updLedger(Ledger ledger){
        financialMapper.updLedger(ledger);
    }

    public void delLedgerByEmpNum(String empNum){
        financialMapper.delLedgerByEmpNum(empNum);
    }

    public void insertSalaryManage(SalaryManage salaryManage){
        financialMapper.insertSalaryManage(salaryManage);
    }

    public void insertSalaryTransfer(SalaryTransfer salaryTransfer){
        financialMapper.insertSalaryTransfer(salaryTransfer);
    }

    @Cacheable(cacheNames = "salaryTransfers",key = "#root.methodName")
    public List<SalaryTransfer> getAllTransfer(){
        return financialMapper.getAllTransfer();
    }
}
