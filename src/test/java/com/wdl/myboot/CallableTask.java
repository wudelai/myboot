package com.wdl.myboot;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableTask implements Callable {
    //创建一个线程池、
    ExecutorService excutorService = Executors.newFixedThreadPool(10);
    @Override
    public Object call() throws Exception {

        return null;
    }
}
