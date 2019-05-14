package com.ten.controller;

import com.ten.entity.LoginLog;
import com.ten.entity.OperateLog;
import com.ten.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/getLogin")
    public List<LoginLog> getAllLogin(){
        return systemService.getAllLogin();
    }

    @RequestMapping(value = "/getOperate")
    public List<OperateLog> getAll(){
        return systemService.getAll();
    }
}
