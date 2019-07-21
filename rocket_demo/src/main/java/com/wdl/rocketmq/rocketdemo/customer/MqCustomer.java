package com.wdl.rocketmq.rocketdemo.customer;

import com.alibaba.rocketmq.common.message.MessageExt;
/**
 * 消费监听接口，所有消费者监听器都需要实现该接口
 *
 * @author wdl
 */
public interface MqCustomer {
    /**
     * 消息消费主方法
     *
     * @param message
     */
    void msgCustomer(MessageExt message);
}
