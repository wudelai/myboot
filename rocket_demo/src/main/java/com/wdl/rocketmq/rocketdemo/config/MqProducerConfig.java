package com.wdl.rocketmq.rocketdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rocket-demo.producer")
public class MqProducerConfig {
    /**
     * 默认的topicID
     */
    private String topicId;
    /**
     * 默认的消费者ID
     */
    private String producerId;
}
