package com.ten.service.impl;


import com.ten.entity.Menu;
import com.ten.entity.Role;
import com.ten.entity.User;
import com.ten.mapper.UserMapper;
import com.ten.service.MenuService;
import com.ten.service.RoleService;
import com.ten.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Override
    public List<User> getAllUsers() {

        return userMapper.getAllUsers();
    }

    @Override
    public User findUserByUsername(String username) {

        return userMapper.findUserByUsername(username);
    }

    @Override
    public List<Menu> getUserMenu(User user) {

        List<Role> userRoles = roleService.getRolesByUser(user);
        List<Menu> roleMenus =  menuService.getMenusByRole(userRoles);
        return roleMenus;
    }

    @Override
    public int getLevelByUser(User user) {
        List<Role> userRoles = roleService.getRolesByUser(user);
        int level=1;
        for (Role role:userRoles){
            level = role.getLevel();
        }
        return level;
    }
}
