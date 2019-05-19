package com.wdl.aopdemo.aop;

import com.wdl.aopdemo.constant.BusinessResultCode;
import com.wdl.aopdemo.constant.CommonConstant;
import com.wdl.aopdemo.exception.BusinessException;
import com.wdl.aopdemo.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 登录验证切面
 *
 * @author wdl
 */
@Aspect
@Component
@Slf4j
@Order(0)
public class AuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)&&!execution(* com.wdl.aopdemo.controller.TestAopController.aopLogin(..))")
    public void auth(){}

    @Before("auth()")
    public void doAuth(){
        //查询cookie
        Cookie cookie = CookieUtil.get(request, CommonConstant.LOGIN_TOKEN);
        if(cookie==null){
            log.warn("[登陆校验]Cookie中查不到token");
            throw new BusinessException(BusinessResultCode.NOT_LOGIN);
        }
        //去redis里查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(CommonConstant.REDIS_LOGIN_TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("[登陆校验]Redis中查不到token");
            throw new BusinessException(BusinessResultCode.NOT_LOGIN);
        }
        //取出redis 的token 做比较
        String inTokenValue = request.getParameter(CommonConstant.LOGIN_TOKEN_KEY);
        log.info("redis 查询出来的tokenValue:{},前端传入的token:{}",tokenValue,inTokenValue);
        if (!tokenValue.equals(inTokenValue)){
            log.warn("[登陆校验]token比较不通过");
            throw new BusinessException(BusinessResultCode.NOT_LOGIN);
        }
        //成功的情况，重新放入redis,设置三十分钟失效，也就是说登录状态三十分钟超时
        redisTemplate.opsForValue().set(String.format(CommonConstant.REDIS_LOGIN_TOKEN_PREFIX,cookie.getValue()),tokenValue,1800, TimeUnit.SECONDS);
    }

}
