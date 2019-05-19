package com.wdl.aopdemo.exception;

import com.wdl.aopdemo.constant.BusinessResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 * 要么 抛 java.lang.RuntimeException or java.lang.Error 非检查性异常, 要么接口要声明异常。这里选择 自定义异常为 运行时异常即可。
 *
 *
 * @author wdl
 */
public class BusinessException extends RuntimeException{
    /**
     * 业务异常代码
     */
    @Getter
    @Setter
    private BusinessResultCode businessResultCode;

    public BusinessException(BusinessResultCode businessResultCode) {
        this.businessResultCode = businessResultCode;
    }
}
