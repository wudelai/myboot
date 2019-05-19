package com.wdl.aopdemo.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractResultVO<T> {
    private int code;
    private String message;
    private T data;
}
