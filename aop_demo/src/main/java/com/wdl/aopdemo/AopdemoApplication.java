package com.wdl.aopdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication/*(scanBasePackages = "com.wdl.*")*/
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopdemoApplication.class, args);
    }

}
