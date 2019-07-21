package com.wdl.rocketmq.rocketdemo.producer;

import com.alibaba.rocketmq.client.producer.SendResult;

public interface MqProducer {
    /**
     * 消息发送
     *
     * @param topic
     * @param tag
     * @param key
     * @param message
     * @return
     */
    SendResult sendMsg(String topic, String tag, String key, String message);
}
