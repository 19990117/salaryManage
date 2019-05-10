package com.ten.service;

import com.ten.entity.Menu;
import com.ten.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User findUserByUsername(String username);

    List<Menu> getUserMenu(User user);
}
