package com.wdl.myboot.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobFactory extends AdaptableJobFactory {
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        return super.createJobInstance(bundle);
    }
}
