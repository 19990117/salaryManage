package com.ten.service;

import com.ten.entity.Role;
import com.ten.entity.User;

import java.util.List;

public interface RoleService {
    List<Role> getRolesByUser(User user);
}
