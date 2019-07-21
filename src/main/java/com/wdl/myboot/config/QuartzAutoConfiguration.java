package com.wdl.myboot.config;

import com.wdl.myboot.annotation.TriggerProperties;
import com.wdl.myboot.quartz.jobs.TestQuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * quartz 自动注册(通过注解动态注册，triggerCron 表达式支持使用注解或配置项获取)
 *
 * @author wdl
 */
@Slf4j
@Configuration
public class QuartzAutoConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, ApplicationContextAware {
    private static final String JOB_SUFIX = "Detail";
    private static final String TRIGGER_SUFIX = "Trigger";
    private static final String DEFAULT_GROUP_NAME = "my_quartz_def_group";
    private Environment environment;
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        //获取配置文件属性
        Binder binder = Binder.get(environment);
        Map<String,Object> beansMap = applicationContext.getBeansWithAnnotation(TriggerProperties.class);
        if (CollectionUtils.isEmpty(beansMap)){
            log.info("没有获取到使用注解的bean");
            return;
        }
        for (Object bean : beansMap.values()) {
            if (bean instanceof QuartzJobBean){
                Class<? extends QuartzJobBean> jobClass = (Class<? extends QuartzJobBean>) bean.getClass();
                TriggerProperties annotation = AnnotationUtils.findAnnotation(jobClass,TriggerProperties.class);
                if (annotation != null){
                    String jobName = jobClass.getSimpleName();
                    //jobDetail 、triggerName名称及bean 名称优先使用注入的值（没有则取job类名+默认后缀）
                    String jobDetailName = StringUtils.hasText(annotation.jobDetailName()) ? annotation.jobDetailName() : jobName + JOB_SUFIX;
                    String triggerName = StringUtils.hasText(annotation.triggerName()) ? annotation.triggerName() : jobName + JOB_SUFIX;
                    //优先使用注入的值（没有则取默认值）
                    String groupName = StringUtils.hasText(annotation.triggerName()) ? annotation.groupName() : DEFAULT_GROUP_NAME;
                    //表达式支持使用占位符
                    String cron = this.environment.resolvePlaceholders(annotation.triggerCron());
                    if (!StringUtils.hasText(cron)){
                        log.warn("==============》job类<{}>没有配置表达式",jobClass.getName());
                        continue;
                    }
                    //注册 jobDetail Bean
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JobDetail.class, () -> {
                        JobDetail jobDetailBean = JobBuilder.newJob(jobClass)
                                .withIdentity(jobDetailName,groupName)
                                .storeDurably()
                                .build();
                        return jobDetailBean;
                    });
                    BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
                    beanDefinitionRegistry.registerBeanDefinition(jobDetailName,beanDefinition);
                    //注册trigger bean
                    BeanDefinitionBuilder beanDefinitionBuilder1 = BeanDefinitionBuilder.genericBeanDefinition(Trigger.class, () -> {
                        //任务执行表达式
                        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
                        //触发器
                        Trigger triggerBean = TriggerBuilder.newTrigger()
                                .forJob(jobDetailName,groupName)
                                .withIdentity(triggerName,groupName)
                                .withSchedule(cronScheduleBuilder)
                                .build();
                        return triggerBean;
                    });
                    BeanDefinition beanDefinition1 = beanDefinitionBuilder1.getRawBeanDefinition();
                    beanDefinitionRegistry.registerBeanDefinition(triggerName,beanDefinition1);
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

}
