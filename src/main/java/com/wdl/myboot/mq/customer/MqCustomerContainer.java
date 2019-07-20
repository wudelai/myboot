package com.wdl.myboot.mq.customer;

import com.wdl.myboot.mq.customer.impl.DefaultCustomer;
import com.wdl.myboot.mq.entry.MqMessage;
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
    private String customerId;
    private String topicTags;
    private boolean started;
    private MqCustomer mqCustomer;
    DefaultCustomer defaultCustomer;
    public MqCustomerContainer(){

    }
    public void start(){
        log.info("开始初始化消费者容器");
        try {
            this.setStarted(true);
            this.intiCustomer();
            log.info("初始化消费者容器完成");
        }catch (Exception e){
            throw new RuntimeException("初始化消费者容器异常",e);
        }
    }

    /**
     * 初始化消费者对象,此处没有实际写消费者场景，模拟参数
     */
    private void intiCustomer(){
        defaultCustomer = new DefaultCustomer();
        defaultCustomer.registerMqListener(new MqListenerConcurrently() {
            @Override
            public void customeMessage(List<MqMessage> mqMessages) {
                for (MqMessage mqMessage: mqMessages){
                    mqCustomer.msgCustomer(mqMessage);
                }
            }
        });
        defaultCustomer.start();
    }

    public void destroy(){
        if (defaultCustomer != null){
            defaultCustomer.shutDown();
        }
    }
}
