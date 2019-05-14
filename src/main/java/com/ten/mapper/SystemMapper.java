package com.ten.mapper;

import com.ten.entity.Logins;
import com.ten.entity.OperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SystemMapper {
    @Select("select * from sys_LoginLog")
    public List<Logins> getAllLogin();

    @Select("select * from sys_OperateLog")
    public List<OperateLog> getAllOperate();
}
