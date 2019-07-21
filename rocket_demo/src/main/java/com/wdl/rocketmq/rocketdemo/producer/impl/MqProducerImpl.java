package com.wdl.rocketmq.rocketdemo.producer.impl;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.wdl.rocketmq.rocketdemo.config.MqProducerConfig;
import com.wdl.rocketmq.rocketdemo.producer.MqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@Component
public class MqProducerImpl implements MqProducer {
    private DefaultMQProducer producer;
    @Autowired
    private MqProducerConfig mqProducerConfig;
    @Value("${spring.rocket-demo.admin-server}")
    private String adminServer;
    @PostConstruct
    private void initProducer(){
        try {
            log.info("producerID:{} init to adminServer:{}",mqProducerConfig.getProducerId(),adminServer);
            producer =  new DefaultMQProducer(mqProducerConfig.getProducerId());
            producer.setNamesrvAddr(adminServer);
            producer.setInstanceName(UUID.randomUUID().toString());
            producer.start();
            log.info("producer start OK");
        }catch (Exception e){
            throw new RuntimeException("初始化生产者异常：",e);
        }
    }
    @Override
    public SendResult sendMsg(String topic, String tag,String key, String message) {
        Message message1 = new Message();
        message1.setKeys(key);
        message1.setBody(message.getBytes());
        message1.setTags(tag);
        if (!StringUtils.hasText(topic)){
            message1.setTopic(mqProducerConfig.getTopicId());
        }else {
            message1.setTopic(topic);
        }
        try {
            SendResult sendResult = producer.send(message1);
            return sendResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
