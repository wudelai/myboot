package com.wdl.myboot.mq.listener;

import com.wdl.myboot.annotation.MqMessageListener;
import com.wdl.myboot.mq.customer.MqCustomer;
import com.wdl.myboot.mq.entry.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * mq 消费者监听器
 *
 * @author wdl
 */
@Slf4j
@Component
@MqMessageListener(customerId = "test_customerId", topicTags = "myboot_topic:taga||tagb||tagc")
public class MqCustomerListener implements MqCustomer {
    @Override
    public void msgCustomer(MqMessage message) {
        log.info("=======监听到消息：{}",message);
    }
}
