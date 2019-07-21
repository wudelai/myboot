package com.wdl.rocketmq.rocketdemo.customer;

import com.alibaba.rocketmq.common.message.MessageExt;
import com.wdl.rocketmq.rocketdemo.annotation.MqMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * mq 消费者监听器
 *
 * @author wdl
 */
@Slf4j
@Component
@MqMessageListener(customerId = "${spring.rocket-demo.customer.customerId}", topicTags = "${spring.rocket-demo.customer.topicTags}")
public class MqCustomerListener implements MqCustomer {
    @Override
    public void msgCustomer(MessageExt message) {
        log.info("=======监听到消息：{}",message);
    }
}
