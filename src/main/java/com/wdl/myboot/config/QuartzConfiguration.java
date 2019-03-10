package com.wdl.myboot.config;

import com.wdl.myboot.quartz.jobs.TestQuartzJob;
import com.wdl.myboot.quartz.jobs.TestQuartzJobThr;
import com.wdl.myboot.quartz.jobs.TestQuartzJobTwo;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {
    @Bean
    public JobDetail testQuartzJobDetail() {
        return JobBuilder.newJob(TestQuartzJob.class).withIdentity("testQuartzJob","mytest_group_name").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzJobTrigger() {
        //任务执行表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(testQuartzJobDetail())
                .withIdentity("testQuartzJobTrigger","mytest_group_name")
                .withSchedule(cronScheduleBuilder)
                .build();
        return trigger;
    }

    @Bean
    public JobDetail testQuartzJobTwoDetail() {
        return JobBuilder.newJob(TestQuartzJobTwo.class).withIdentity("testQuartzJobTwo","mytest_group_name").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzJobTwoTrigger() {
        //任务执行表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(testQuartzJobTwoDetail())
                .withIdentity("testQuartzJobTwoTrigger","mytest_group_name")
                .withSchedule(cronScheduleBuilder)
                .build();
        return trigger;
    }
    @Bean
    public JobDetail testQuartzJobThrDetail() {
        return JobBuilder.newJob(TestQuartzJobThr.class).withIdentity("testQuartzJobThr","mytest_group_name").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzJobThrTrigger() {
        //任务执行表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/20 * * * * ?");
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(testQuartzJobThrDetail())
                .withIdentity("testQuartzJobThrTrigger","mytest_group_name")
                .withSchedule(cronScheduleBuilder)
                .build();
        return trigger;
    }

}
