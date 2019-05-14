package com.ten.aspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ten.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoginLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoginLogAspect.class);

    @Pointcut("execution(public * com.ten.controller.LoginController.login(String,String))")
    public void login(){}



//    @AfterReturning("login()")
    @After("login()")
    public void LogRequestInfo(JoinPoint joinPoint) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuffer requestLog = new StringBuffer();
        requestLog.append("请求信息：")
                .append("URL = {" + request.getRequestURI() + "},\t")
                .append("HTTP_METHOD = {" + request.getMethod() + "},\t")
                .append("IP = {" + request.getRemoteAddr() + "},\t")
                .append("CLASS_METHOD = {" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "},\t");



        if(joinPoint.getArgs().length == 0) {
            requestLog.append("username = {} ");
        } else {
            requestLog.append("username = " + new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(joinPoint.getArgs()[0]) + "");
        }
        if (user!=null){
            requestLog.append("登录成功");
        }else {
            requestLog.append("登录失败");
        }

        logger.warn(requestLog.toString());
    }


}
