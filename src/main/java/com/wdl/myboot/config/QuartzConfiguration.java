package com.wdl.myboot.config;

import com.wdl.myboot.quartz.jobs.TestQuartzJob;
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

}
