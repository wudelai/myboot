package com.wdl.myboot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 消息监听，所有消费者监听器都需要使用该注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqMessageListener {
    /**
     * 消费者ID(支持占位符)
     */
    String customerId();

    /**
     * topicId 和 标签（支持占位符）
     */
    String topicTags();

}
