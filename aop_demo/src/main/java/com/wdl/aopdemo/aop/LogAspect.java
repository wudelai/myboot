package com.wdl.aopdemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pcut(){
    }

    @Before("pcut()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        log.info("name:{}",signature.getName());
    }
}
