package com.wdl.myboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class SchedulerConfig implements SchedulerFactoryBeanCustomizer {
    @Autowired
    DataSource dataSource;
    @Autowired
    QuartzJobFactory quartzJobFactory;
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setJobFactory(quartzJobFactory);
        schedulerFactoryBean.setDataSource(dataSource);
        //重写已存在的job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //启动延时
        schedulerFactoryBean.setStartupDelay(1000);
        //容器关闭时，停止任务
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
    }
}
