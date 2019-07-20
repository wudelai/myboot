package com.wdl.myboot.mq.customer.impl;

import com.wdl.myboot.mq.customer.MqListenerConcurrently;
import com.wdl.myboot.mq.MqQueue;
import com.wdl.myboot.mq.entry.MqMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 消费者实现(主要是通过一个线程不断的去拉取消息队列的消息)
 *
 * @author wdl
 */
@Slf4j
public class DefaultCustomerImpl {
    private MqListenerConcurrently mqListener;
    private final DefaultCustomer defaultCustomer;
    private final ScheduledExecutorService executors ;
    private MqListenerConcurrently mqListenerConcurrently;

    public DefaultCustomerImpl(DefaultCustomer defaultCustomer){
        this.defaultCustomer = defaultCustomer;
        this.executors =  Executors.newSingleThreadScheduledExecutor();
    }
    /**
     * 消费者启动
     */
    public void start() {
        log.info("DefaultCustomerImpl start....");
        executors.scheduleAtFixedRate(() -> {
            MqMessage message = MqQueue.pollQueue();
            if (message != null) {
                List<MqMessage> mqMessageList = new ArrayList<>();
                mqMessageList.add(message);
                mqListener.customeMessage(mqMessageList);
            }
        },1000L,100L, TimeUnit.MILLISECONDS);
        log.info("DefaultCustomerImpl start OK");
    }
    /**
     * 消费者关闭
     */
    public void shutDown() {
        executors.shutdown();
        log.info("shutDown OK");
    }
    public void registerMqListener(MqListenerConcurrently messageListener) {
        this.mqListener = messageListener;
    }
}
