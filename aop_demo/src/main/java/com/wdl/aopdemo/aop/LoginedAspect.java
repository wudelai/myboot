package com.wdl.aopdemo.aop;

import com.wdl.aopdemo.constant.BusinessResultCode;
import com.wdl.aopdemo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class LoginedAspect {
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pcut(){
    }
    @Before("pcut()")
    public void deoBefore(JoinPoint joinPoint) {
        log.info("方法执行前...");
        log.info("url:" + request.getRequestURI());
        log.info("ip:" + request.getRemoteHost());
        log.info("method:" + request.getMethod());
        log.info("class_method:" + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        log.info("args:" + joinPoint.getArgs());
    }

    @After("pcut()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("方法执行后...");
    }

    @AfterReturning(returning = "result", pointcut = "pcut()")
    public void doAfterReturning(Object result) {
        log.info("执行返回值：" + result);
    }

    @Around("pcut()")
    public Object trackInfo(ProceedingJoinPoint pjp) throws BusinessException,Throwable {
        String remoteHost = request.getRemoteHost();
        HttpSession session = request.getSession();
//        if (session != null && session.getAttribute("token") == null){
//            log.info("-------------没有登录-------------");
//            throw new BusinessException(BusinessResultCode.NOT_LOGIN);
//        }
//        log.info("-------------登录通过-------------");
        return pjp.proceed();
    }
}
