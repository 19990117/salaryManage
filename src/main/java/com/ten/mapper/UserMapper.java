package com.ten.mapper;


import com.ten.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> getAllUsers();

    public User findUserByUsername(String username);
}
