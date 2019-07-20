package com.wdl.myboot.mq.producer;

import com.wdl.myboot.mq.entry.MqMessage;

public interface MqProducer {
    boolean msgSend(MqMessage mqMessage);
}
