package com.ten.mapper;

import com.ten.entity.Role;
import com.ten.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    public List<Role> getRolesByUser(User user);
}
