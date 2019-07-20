package com.wdl.myboot.mq.producer.impl;

import com.wdl.myboot.mq.producer.MqProducer;
import com.wdl.myboot.mq.MqQueue;
import com.wdl.myboot.mq.entry.MqMessage;
import org.springframework.stereotype.Service;

@Service
public class MqProducerImpl implements MqProducer {
    @Override
    public boolean msgSend(MqMessage mqMessage) {
        //模拟生产消息
        boolean flag = MqQueue.addQueue(mqMessage);
        return flag;
    }
}
