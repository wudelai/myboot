package com.wdl.myboot.controller;

import com.wdl.myboot.service.IRedisClient;
import com.wdl.myboot.service.IShortUrlService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class RedisClientController {
    private static final Log log = LogFactory.getLog(RedisClientController.class);
    @Autowired
    private IRedisClient redisClusterClient;
    @Autowired
    private IShortUrlService shortUrlService;

    @RequestMapping(value = "/testRedisSet")
    @ResponseBody
    public String testRedis(@RequestParam(value = "key",required = false)String key, @RequestParam(value = "value",required = false)String value){
        long start = System.currentTimeMillis();
        log.info("into testRedis.........");
        redisClusterClient.set(key,value);
        log.info("out testRedis.........userTime:"+(System.currentTimeMillis()-start)+" ms");
        shortUrlService.insertShortUrl(1,1);
        return "success";
    }

    @RequestMapping(value = "/testRedisGet")
    @ResponseBody
    public String testRedis(@RequestParam(value = "key",required = false)String key){
        long start = System.currentTimeMillis();
        log.info("into testRedis.........");
        String value = redisClusterClient.get(key);
        log.info("out testRedis.........userTime:"+(System.currentTimeMillis()-start)+" ms");;
        return value;
    }
}
