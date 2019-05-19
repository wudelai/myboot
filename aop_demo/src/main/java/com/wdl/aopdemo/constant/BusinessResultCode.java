package com.wdl.aopdemo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum BusinessResultCode {
    SUCCESS(200,"请求成功"),
    ERROR(500,"系统错误"),
    NOT_LOGIN(403,"请先登录"),
    ;
    private int code;
    private String message;
}
