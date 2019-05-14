package com.ten.shrio;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/*
* Shiro配置类
* */
@Configuration
public class ShiroConfig {



    @Bean(name = "sessionDAO")
    public MemorySessionDAO getMemorySessionDAO() {
        return new MemorySessionDAO();
    }

    @Bean(name = "sessionIdCookie")
    public SimpleCookie getSimpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("SHRIOSESSIONID");
        return simpleCookie;
    }
    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(getMemorySessionDAO());
        sessionManager.setSessionIdCookie(getSimpleCookie());
        return sessionManager;
    }
    //配置session的缓存管理器，没有使用redis，所以默认就好
    @Bean(name = "shiroCacheManager")
    public MemoryConstrainedCacheManager getMemoryConstrainedCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /*
    *  创建ShiroFilterFactorBean
    * */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加Shiro内置过滤器
        /*
        *   常用的过滤器：
        *       anon:   无需认证（登录）可以直接访问
        *       authc:  必须认证才可以访问
        *       user:   使用了rememberMe的功能才可以访问
        *       perms:  必须得到资源权限才可以访问
        *       role:   必须得到角色权限才可以访问
        *
        * */

        Map<String, String> filterMap = new LinkedHashMap<>();

        //放行
        filterMap.put("/user/allusers", "anon");
        filterMap.put("/ten/login", "anon");
        filterMap.put("/ten/loginOut", "anon");

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/user/*","perms[user]");
        filterMap.put("/personnel/*","perms[personnel]");
        filterMap.put("/financial/*","perms[financial]");
        filterMap.put("/department/*","perms[department]");
        filterMap.put("/system/*","perms[system]");
        filterMap.put("/ten/testLogin", "perms[Login]");

        //必须认证（登录）
        filterMap.put("/*", "authc");

        //未登录状态访问其他url
        shiroFilterFactoryBean.setLoginUrl("/ten/needLogin");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/ten/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);


        return shiroFilterFactoryBean;
    }

    /*
    *  创建DefaultWebSecurityManager
    *  @Qualifier("userRealm")从下面方法中得到传递的参数userRealm
    * */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联Realm
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(getDefaultWebSessionManager());

        return securityManager;
    }


    /*
    *  创建Realm
    * */
    @Bean(name = "userRealm")
    public  UserRealm getRealm() {
        return  new UserRealm();
    }

    /**
     *  自动创建代理，没有这个鉴权可能会出错
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * 使用代理方式；所以需要开启代码支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager((SecurityManager) securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
