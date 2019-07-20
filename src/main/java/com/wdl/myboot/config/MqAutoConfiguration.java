package com.wdl.myboot.config;

import com.wdl.myboot.annotation.MqMessageListener;
import com.wdl.myboot.mq.customer.MqCustomer;
import com.wdl.myboot.mq.customer.MqCustomerContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
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
        if (clazz.isAssignableFrom(MqCustomer.class)){
            throw new IllegalArgumentException("class:"+clazz.getName()+" is not interface of "+MqCustomer.class.getName());
        }else{
            MqMessageListener messageListener = AnnotationUtils.findAnnotation(clazz,MqMessageListener.class);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(MqCustomerContainer.class);
            beanDefinitionBuilder.addPropertyValue("customerId",this.environment.resolvePlaceholders(messageListener.customerId()));
            beanDefinitionBuilder.addPropertyValue("topicTags",this.environment.resolvePlaceholders(messageListener.topicTags()));
            beanDefinitionBuilder.addPropertyValue("mqCustomer",beanClass);
            beanDefinitionBuilder.setDestroyMethodName("destroy");
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
//            ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
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
