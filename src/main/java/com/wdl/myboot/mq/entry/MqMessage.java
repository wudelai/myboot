package com.wdl.myboot.mq.entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * 消息实体类
 *
 * @author wdl
 */
@Data
public class MqMessage {
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息Id
     */
    private String msgId;
    /**
     * 消息key
     */
    private String msgKey;
    /**
     * 消息标签
     */
    private String tag;
    /**
     * 消息主体
     */
    private String content;
    /***/
    private ObjectMapper objectMapper;
}
