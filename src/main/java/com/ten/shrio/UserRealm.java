package com.ten.shrio;

import com.ten.entity.User;
import com.ten.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//自定义realm
public class UserRealm extends AuthorizingRealm {


    @Autowired
    UserService userService;

    /*
    *  执行授权逻辑
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {


        //权限信息对象info，用来存放查出的用户的所有的角色（role）以及权限（permission）

        User user = (User)principalCollection.getPrimaryPrincipal();
        int level = userService.getLevelByUser(user);
        AuthorizationInfo authorizationInfo = new AuthorizationInfo() {
            @Override
            public Collection<String> getRoles() {
                return null;
            }

            @Override
            public Collection<String> getStringPermissions() {
                List<String> perms = new LinkedList<>();
                perms.add("user");
                if (level == 2){
                    perms.add("personnel");
                } else if (level == 3){
                    perms.add("financial");
                } else if (level == 4){
                    perms.add("department");
                } else {
                    perms.add("system");
                    perms.add("personnel");
                    perms.add("financial");
                    perms.add("department");
                }
                perms.add("Login");
                return perms;
            }

            @Override
            public Collection<Permission> getObjectPermissions() {
                return null;
            }
        };

        return authorizationInfo;
    }


    /*
    *  执行认证的逻辑
    *  编写shiro判断逻辑，判断用户名和密码
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //authenticationToken为前端传递过来的Token，带着username和password
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();

        User user = userService.findUserByUsername(username);

        if(user == null) {
            //如果返回为null，shrio底层会抛出UnKnowAccountException
            return null;
        }

        //判断密码，shrio自动判断
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
