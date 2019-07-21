package com.wdl.rocketmq.rocketdemo.customer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 消费者容器，用于注册所有使用了MqMessageListener注解的对象到spring
 *
 * @author wdl
 */
@Slf4j
@Getter
@Setter
public class MqCustomerContainer{
    private String namesrvAddr;
    private String customerId;
    private String topicTags;
    private boolean started;
    private MqCustomer mqCustomer;
    private DefaultMQPushConsumer defaultMQPushConsumer;
    public MqCustomerContainer(){

    }
    public void start(){
        log.info("开始初始化消费者容器");
        try {
            this.setStarted(true);
            this.intiCustomer();
            log.info("初始化消费者容器完成..");

        }catch (Exception e){
            throw new RuntimeException("初始化消费者容器异常",e);
        }
    }

    /**
     * 初始化消费者对象,此处没有实际写消费者场景，模拟参数
     */
    private void intiCustomer() throws MQClientException {
        defaultMQPushConsumer = new DefaultMQPushConsumer(customerId);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        String []  topicArray= topicTags.split(";");
        for (String topicTag: topicArray){
            String[] topicTagArray = topicTag.split(":");
            String tags = topicTagArray.length<2?"*":topicTagArray[1];
            String topic = topicTagArray[0];
            defaultMQPushConsumer.subscribe(topic,tags);
        }
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    for (MessageExt messageExt: list){
                        mqCustomer.msgCustomer(messageExt);
                    }
                }catch (Exception e){
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        defaultMQPushConsumer.start();
    }

    public void destroy(){
        if (defaultMQPushConsumer != null){
            defaultMQPushConsumer.shutdown();
        }
    }
}
