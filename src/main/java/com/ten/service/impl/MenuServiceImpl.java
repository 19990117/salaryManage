package com.ten.service.impl;

import com.ten.entity.Menu;
import com.ten.entity.Role;
import com.ten.mapper.MenuMapper;
import com.ten.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenusByRole(List<Role> userRoles) {

        List<Menu> roleMenus = menuMapper.getMenusByRole(userRoles);
        return roleMenus;
    }
}
