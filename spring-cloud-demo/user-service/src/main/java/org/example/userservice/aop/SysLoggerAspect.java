package org.example.userservice.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.userservice.annotation.SysLogger;
import org.example.userservice.bean.SysLog;
import org.example.userservice.service.LoggerService;
import org.example.userservice.util.HttpUtils;
import org.example.userservice.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SysLoggerAspect {

    @Autowired
    private LoggerService loggerService;


    @Pointcut("@annotation(org.example.userservice.annotation.SysLogger)")
    public void loggerPointCut() {
    }

    @Before("loggerPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLog sysLog = new SysLog();
        SysLogger sysLogger = method.getAnnotation(SysLogger.class);
        if (sysLogger != null) {
            // 注解上的描述
            sysLog.setOperation(sysLogger.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = methodSignature.getName();
        sysLog.setMethod(className + "." +methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        for (Object obj : args) {
            params += JSON.toJSONString(obj);
        }
        if (!StringUtils.isEmpty(params)) {
            sysLog.setParams(params);
        }
        // 设置IP地址
        sysLog.setIp(HttpUtils.getIpAddress());
        // 用户名
        String username = UserUtils.getCurrentPrinciple();
        if (!StringUtils.isEmpty(username)) {
            sysLog.setUsername(username);
        }
        sysLog.setCreateDate(new Date());
        // 保存系统日志
        loggerService.log(sysLog);
    }


}
