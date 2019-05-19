package com.wdl.handler.util;

import org.slf4j.MDC;

import java.util.UUID;

public class MDCUtil {
    private static final String MDC_KEY = "testMdc";

    /**
     * 设置一个日志流程ID
     */
    public static void setLogId(){
        MDC.put(MDC_KEY, UUID.randomUUID().toString());
    }

    /**
     * 设置一个日志流程ID
     * @param value
     */
    public static void setLogId(String value){
        MDC.put(MDC_KEY, value);
    }

    /**
     * 获取一个日志流程ID
     */
    public static void getLogId(){
        MDC.get(MDC_KEY);
    }

    /**
     * 删除一个日志流程ID
     */
    public static void removeLogId(){
        MDC.remove(MDC_KEY);
    }

    /**
     * 清除所有常量值
     */
    public static void clear(){
        MDC.clear();
    }
}
