package com.wdl.handler.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessResultCode {
    SUCCESS(200,"请求成功"),
    ERROR(500,"系统错误"),
    FILE_EMPTY(508,"文件为空"),
    FILE_TYPE_INCORRECT(509,"文件类型不正确"),
    FILE_TYPE_UNRECOGNIZABLE(510,"无法识别的文件类型"),
    ;
    private int code;
    private String message;
}
