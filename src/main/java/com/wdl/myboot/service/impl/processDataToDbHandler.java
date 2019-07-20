package com.wdl.myboot.service.impl;

import com.wdl.myboot.service.IProcessDataHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class processDataToDbHandler implements IProcessDataHandler {
    @Override
    public void processData() {
        log.info("我是保存DB");
    }
}
