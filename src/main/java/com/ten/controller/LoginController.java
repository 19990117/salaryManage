package com.ten.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ten.entity.Menu;
import com.ten.entity.User;
import com.ten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "ten")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    MemorySessionDAO sessionDAO;


    @RequestMapping(value = "/login")
    public List<Menu> login(@RequestParam(required = true) String username, @RequestParam(required = true) String password ) {

        // 获取subject
        Subject subject = SecurityUtils.getSubject();

        // 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        //Map<String ,Object > resMap = new LinkedHashMap<>();
        //resMap.put("res_code","000");
        //resMap.put("res_msg","success");
        try {
            // 执行登录方法
            subject.login(token);

            // 验证用户是否已经登录
//            Collection<Session> sessions = sessionDAO.getActiveSessions();
//
//            if (subject.isAuthenticated()) {
//                for (Session session:sessions) {
//                    if(username.equals(session.getAttribute("loginedUser"))) {
//                        if(session.getId().equals(subject.getSession().getId())) {
//                            subject.logout();
//                            resMap.put("res_body","用户已登录");
//                        } else {
//                            resMap.put("res_body","用户在别处登录");
//                            session.setTimeout(0);
//                        }
//                        return resMap;
//                    }
//                }
//            }
//            subject.getSession().setAttribute("loginedUser",username);
            // 设置session过期时间
            //subject.getSession().setTimeout(50000);
            // 获取用户对象
            User user = (User)subject.getPrincipal();

            // 获取用户菜单
            List<Menu> menus = userService.getUserMenu(user);

            // 合并菜单
            List<Menu> mergedMenus = new ArrayList<>();

            // 一级菜单直接放入mergedMenus
            for(Menu menu:menus) {
                if(menu.getParent().equals("0")) {
                    mergedMenus.add(menu);
                }
            }
            // 二级菜单放在一级菜单下
            for(Menu menu:menus) {
                if(menu.getParent()!=null) {
                    for(Menu mergedMenu:mergedMenus) {
                        // 找到父菜单
                        if(String.valueOf(mergedMenu.getId()).equals(menu.getParent())) {
                            // 如果父节点没有子节点List就创建一个
                            List<Menu> childMenus;
                            if (mergedMenu.getChildMenus() == null) {
                                childMenus = new ArrayList<>();
                            } else {
                                childMenus = mergedMenu.getChildMenus();
                            }
                            childMenus.add(menu);
                            mergedMenu.setChildMenus(childMenus);
                            break;
                        }
                    }
                }
            }

            return mergedMenus;

        } catch (UnknownAccountException e) {
            List<Menu> mergedMenus = new ArrayList<>();
            Menu menu = new Menu();
            menu.setMsg("用户不存在");
            mergedMenus.add(menu);
            return mergedMenus;
        } catch (IncorrectCredentialsException e) {
            List<Menu> mergedMenus = new ArrayList<>();
            Menu menu = new Menu();
            menu.setMsg("密码错误");
            mergedMenus.add(menu);
            return mergedMenus;
            //resMap.put("res_code","002");
            //resMap.put("res_msg","Error");
            //resMap.put("res_body","");
            //return resMap;
        }

    }

    @RequestMapping(value = "/loginOut")
    public void loginOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @RequestMapping("/testLogin")
    public String testSelect() {
        return "testLogin";
    }

    /**
     * 未登录就请求其他url
     */
    @RequestMapping("/needLogin")
    public Map needLogin() {
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","you need login");
        return info;
    }

    /**
     * 未授权
     */
    @RequestMapping("/noAuth")
    public Map<String, Object> noAuth() {
        HashMap<String,Object> info = new HashMap<>();
        info.put("info","you have no auth");
        return info;
    }

}
