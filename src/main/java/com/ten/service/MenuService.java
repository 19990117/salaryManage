package com.ten.service;

import com.ten.entity.Menu;
import com.ten.entity.Role;

import java.util.List;

public interface MenuService {
    List<Menu> getMenusByRole(List<Role> userRoles);
}
