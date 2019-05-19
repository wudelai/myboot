package com.wdl.aopdemo.exception;

import com.wdl.aopdemo.constant.BusinessResultCode;
import com.wdl.aopdemo.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Component
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public Object besinessExceptionHandler(BusinessException e){
       log.info("业务异常：",e);
       BusinessResultCode businessResultCode = e.getBusinessResultCode();
       log.info("businessResultCode",businessResultCode);
       return Results.businessError(businessResultCode);
    }

    @ExceptionHandler(Throwable.class)
    public Object ExceptionHandler(Throwable e){
        log.error("系统异常：",e);
        return Results.error();
    }
}
