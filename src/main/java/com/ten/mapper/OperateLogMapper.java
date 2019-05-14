package com.ten.mapper;

import com.ten.entity.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateLogMapper {
    @Insert("insert into sys_OperateLog(type,ip,username,userid,time,description) values(#{type},#{ip},#{username},#{userid},#{time},#{description})")
    public void insert(OperateLog operateLog);
}
