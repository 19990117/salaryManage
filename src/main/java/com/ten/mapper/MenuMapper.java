package com.ten.mapper;

import com.ten.entity.Menu;
import com.ten.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> getMenusByRole(List<Role> userRoles);
}
