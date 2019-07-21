package com.wdl.myboot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface TriggerProperties {
    /**
     * job名称
     */
    String jobDetailName() default "";

    /**
     * trigger名称
     */
    String triggerName() default "";

    /**
     * 表达式
     */
    String triggerCron();
    /**
     * 分组名称
     */
    String groupName() default "";
}
