package com.ten.service.impl;

import com.ten.entity.Role;
import com.ten.entity.User;
import com.ten.mapper.RoleMapper;
import com.ten.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUser(User user) {
       List<Role> userRoles =  roleMapper.getRolesByUser(user);
       return userRoles;
    }
}
