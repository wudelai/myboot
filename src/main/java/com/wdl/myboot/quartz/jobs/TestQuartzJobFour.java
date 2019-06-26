package com.wdl.myboot.quartz.jobs;

import com.wdl.myboot.annotation.TriggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@TriggerProperties(jobDetailName = "TestQuartzJobFourJobDetail", triggerName = "TestQuartzJobFourTrigger", triggerCron = "0/5 * * * * ?", triggerCronPropertiesName = "task.quartz.trigger.properties.testquartzron", groupName = "test_defalt_group")
public class TestQuartzJobFour extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("=====================TestQuartzJobFour running,nowTime:{}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }
}
