package com.ten.service;

import com.ten.entity.LoginLog;
import com.ten.entity.Logins;
import com.ten.entity.OperateLog;
import com.ten.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService {
    @Autowired
    private SystemMapper systemMapper;

    public List<LoginLog> getAllLogin(){
        List<LoginLog> loginLogs = new ArrayList<>();
        List<Logins> logins = systemMapper.getAllLogin();
        for (Logins login:logins){
            String id = String.valueOf(login.getId());
            String time = login.getDate();
            String message = login.getMessage();
            if (message.indexOf("IP") == -1){
                continue;
            }
            if (message.indexOf("CLASS") == -1){
                continue;
            }
            if (message.indexOf("username") == -1){
                continue;
            }
            String ip = message.substring(message.indexOf("IP")+4,message.indexOf("CLASS")-2);
            String user = message.substring(message.indexOf("username")+12,message.length()-5);
            String status = message.substring(message.length()-4,message.length());
            LoginLog log = new LoginLog(id,user,time,status,ip);
            loginLogs.add(log);
        }
        return loginLogs;
    }

    public List<OperateLog> getAll(){
        return systemMapper.getAllOperate();
    }
}
