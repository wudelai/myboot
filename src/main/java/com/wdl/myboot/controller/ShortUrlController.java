package com.wdl.myboot.controller;

import com.wdl.myboot.service.IRedisClient;
import com.wdl.myboot.service.IShortUrlService;
import com.wdl.myboot.service.IUserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/share")
//@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class ShortUrlController {

    private static final Log log = LogFactory.getLog(ShortUrlController.class);
    @Autowired
    private IShortUrlService shortUrlService;

    @RequestMapping(value = "/a/{shortUrl}")
    public String queryUrlByShortUrl(@PathVariable("shortUrl")String shortUrl, HttpServletResponse response){
        long start = System.currentTimeMillis();
        System.out.println("into queryUrlByShortUrl.........");
        String oldUrl = shortUrlService.queryUrlByShortUrl(shortUrl);
        System.out.println("oldUrl:"+ oldUrl);
        System.out.println("out queryUrlByShortUrl.........userTime:"+(System.currentTimeMillis()-start)+" ms");
//        response.setStatus(302);
//        response.sendRedirect(oldUrl);
        return "redirect:"+oldUrl;
    }
    @RequestMapping(value = "/createdShortUrl")
    public String createdShortUrl(@RequestParam(value = "length",required = false)int length, @RequestParam(value = "count",required = false)int count){
        long start = System.currentTimeMillis();
        System.out.println("into createdShortUrl.........");
        shortUrlService.insertShortUrl(length,count);
        System.out.println("out createdShortUrl.........userTime:"+(System.currentTimeMillis()-start)+" ms");
        return "index";
    }

}
