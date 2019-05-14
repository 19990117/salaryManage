package com.ten.aspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ten.entity.OperateLog;
import com.ten.entity.User;
import com.ten.mapper.OperateLogMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;

@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    OperateLogMapper mapper;

    @Pointcut("execution(public * com.ten.controller.*.*(..))&& !execution(* com.ten.controller.LoginController.*(..))&& !execution(* com.ten.controller.*.get*(..))")
    public void Operation(){}

    @After("Operation()")
    public void insertLog(JoinPoint joinPoint) throws JsonProcessingException {

        OperateLog operateLog = new OperateLog();

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();



        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        OperationLog myLog = method.getAnnotation(OperationLog.class);
        if (myLog != null) {
            String value = myLog.value();
            //插入操作类型
            operateLog.setType(value);
        }


        operateLog.setIp(request.getRemoteAddr());

        if (user!=null){
            //记录操作者
            operateLog.setUserid(user.getEmpNum());
            operateLog.setUsername(user.getUsername());
        }
        //记录操作时间
        operateLog.setTime(new Timestamp(System.currentTimeMillis()).toString());
        //记录操作内容
        if(joinPoint.getArgs().length == 0) {
            operateLog.setDescription("操作失败");
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i=0;i<joinPoint.getArgs().length;i++){
                stringBuffer.append("parameter-"+i+":"+new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                   .writeValueAsString(joinPoint.getArgs()[i])+" ");
            }
            operateLog.setDescription(stringBuffer.toString());
        }

        System.out.println(operateLog.toString());

        mapper.insert(operateLog);
    }

//    @Pointcut("execution(public * com.ten.controller.*.upd*(..))")
//    public void updateOperation(){}
//
//    @After("updateOperation()")
//    public void updateLog(JoinPoint joinPoint) throws JsonProcessingException {
//
//        OperateLog operateLog = new OperateLog();
//
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User) subject.getPrincipal();
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//
//
//        //从切面织入点处通过反射机制获取织入点处的方法
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        //获取切入点所在的方法
//        Method method = signature.getMethod();
//
//        //获取操作
//        OperationLog myLog = method.getAnnotation(OperationLog.class);
//        if (myLog != null) {
//            String value = myLog.value();
//            //插入操作类型
//            operateLog.setType(value);
//        }
//
//        //插入ip
//        operateLog.setIp(request.getRemoteAddr());
//
//        if (user!=null){
//            //记录操作者
//            operateLog.setUserid(user.getEmpNum());
//            operateLog.setUsername(user.getUsername());
//        }
//        //记录操作时间
//        operateLog.setTime(new Date());
//        //记录操作内容
//        if(joinPoint.getArgs().length == 0) {
//            operateLog.setDesc("操作失败");
//        } else {
//            StringBuffer stringBuffer = new StringBuffer();
//            for (int i=0;i<joinPoint.getArgs().length;i++){
//                stringBuffer.append("parameter-"+i+":"+new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                        .writeValueAsString(joinPoint.getArgs()[i])+" ");
//            }
//            operateLog.setDesc(stringBuffer.toString());
//        }
//
//        System.out.println(operateLog.toString());
//
//    }
}
