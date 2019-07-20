package com.wdl.myboot.mq.customer;

import com.wdl.myboot.mq.entry.MqMessage;

import java.util.List;

/**
 *
 */
public interface MqListenerConcurrently {
    void customeMessage(List<MqMessage> mqMessage);
}
