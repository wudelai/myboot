package com.wdl.aopdemo.controller;

import com.wdl.aopdemo.constant.CommonConstant;
import com.wdl.aopdemo.entry.UserEntry;
import com.wdl.aopdemo.util.MD5;
import com.wdl.aopdemo.vo.ResultVO;
import com.wdl.aopdemo.vo.Results;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping("/aopDemo")
public class TestAopController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/aopTest")
    public ResultVO testAopDemo(HttpServletRequest request){

        return Results.success();
    }

    /**
     * 模拟登录，没有实际进行登录
     *
     * @param userEntry
     * @return
     */
    @RequestMapping("/aopLogin")
    public String aopLogin(UserEntry userEntry, HttpServletResponse response){
        String userId = userEntry.getUserId();
        String passWord = userEntry.getPassWord();
        //生成tokenValue 返回前端
        String tokenValue = MD5.MD5Encode(userId+passWord+ UUID.randomUUID().toString(),"utf-8");
        //将一个随机数放入cookie
        String randomStr = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(CommonConstant.LOGIN_TOKEN,randomStr);
        response.addCookie(cookie);
        //将tokenValue 放入redis
        log.info("=============>>>>>tokenValue:"+tokenValue);
        stringRedisTemplate.opsForValue().set(String.format(CommonConstant.REDIS_LOGIN_TOKEN_PREFIX,randomStr),tokenValue,1800, TimeUnit.SECONDS);

        return tokenValue;
    }
}
