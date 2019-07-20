package com.wdl.myboot.mq.customer.impl;

import com.wdl.myboot.mq.customer.MqListenerConcurrently;
import lombok.extern.slf4j.Slf4j;

/**
 *消费者的默认实现
 *
 * @author wdl
 */
@Slf4j
public class DefaultCustomer{

    private MqListenerConcurrently mqListener;
    protected final transient DefaultCustomerImpl defaultCustomerImpl;
    public DefaultCustomer (){
        this.defaultCustomerImpl = new DefaultCustomerImpl(this);
    }
    /**
     * 消费者启动
     */
    public void start() {
        this.defaultCustomerImpl.start();
    }
    /**
     * 消费者关闭
     */
    public void shutDown() {
        this.defaultCustomerImpl.shutDown();
    }

    public void registerMqListener(MqListenerConcurrently mqListener) {
        this.mqListener = mqListener;
        this.defaultCustomerImpl.registerMqListener(mqListener);
    }

}
