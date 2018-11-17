package com.wdl.myboot.controller;

import com.wdl.myboot.service.IShortUrlService;
import com.wdl.myboot.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/share")
public class ShortUrlController {
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
