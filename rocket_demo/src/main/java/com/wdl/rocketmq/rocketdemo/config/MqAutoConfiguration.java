package com.wdl.rocketmq.rocketdemo.config;

import com.wdl.rocketmq.rocketdemo.annotation.MqMessageListener;
import com.wdl.rocketmq.rocketdemo.customer.MqCustomer;
import com.wdl.rocketmq.rocketdemo.customer.MqCustomerContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@Order
public class MqAutoConfiguration implements ApplicationContextAware, EnvironmentAware {
    @Value("${spring.rocket-demo.admin-server}")
    private String adminServer;
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    private Environment environment;
    private ConfigurableApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    @PostConstruct
    private void initConfig(){
        log.info("开始注册消费者容器");
        Map<String,Object> beansMap = applicationContext.getBeansWithAnnotation(MqMessageListener.class);
        log.info("===========beansMap:{}",beansMap);
        if (!Objects.isNull(beansMap)){
            beansMap.forEach(this::registerCustomer);
        }
    }

    private void registerCustomer(String beanName, Object beanClass) {
        Class clazz = AopUtils.getTargetClass(beanClass);
        if (!MqCustomer.class.isAssignableFrom(clazz)){
            throw new IllegalArgumentException("class:"+clazz.getName()+" is not interface of "+MqCustomer.class.getName());
        }else{
            MqMessageListener messageListener = AnnotationUtils.findAnnotation(clazz,MqMessageListener.class);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(MqCustomerContainer.class);
            beanDefinitionBuilder.addPropertyValue("customerId",this.environment.resolvePlaceholders(messageListener.customerId()));
            beanDefinitionBuilder.addPropertyValue("topicTags",this.environment.resolvePlaceholders(messageListener.topicTags()));
            beanDefinitionBuilder.addPropertyValue("mqCustomer",beanClass);
            beanDefinitionBuilder.addPropertyValue("namesrvAddr",adminServer);
            beanDefinitionBuilder.setDestroyMethodName("destroy");
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            DefaultListableBeanFactory registry = new DefaultListableBeanFactory();
            String generateBeanName = beanNameGenerator.generateBeanName(beanDefinition,registry);
            registry.registerBeanDefinition(generateBeanName,beanDefinition);
            log.info("generateBeanName:{} register success",generateBeanName);
            MqCustomerContainer mqCustomerContainer = registry.getBean(generateBeanName,MqCustomerContainer.class);
            if (!mqCustomerContainer.isStarted()){
                mqCustomerContainer.start();
            }
        }
    }
}
