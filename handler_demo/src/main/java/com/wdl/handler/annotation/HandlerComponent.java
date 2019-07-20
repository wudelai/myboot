package com.wdl.handler.annotation;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 处理链注解
 *
 * @author wdl
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface HandlerComponent {
    /**
     * handler 处理链
     *
     * @return
     */
    String[] value() default "";

    /**
     * 需要执行的handler 名称
     *
     * @return
     */
    String threadHandler() default "";

    /**
     * 策略模式需要指定的handler 分组
     *
     * @return
     */
    String group() default "";
}
