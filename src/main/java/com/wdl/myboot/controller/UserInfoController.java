package com.wdl.myboot.controller;

import com.wdl.myboot.model.UserInfo;
import com.wdl.myboot.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        System.out.println("into.........");
        return "index";
    }
//    @RequestMapping
//    public String share(){
//        System.out.println("into.........");
//        return "index";
//    }
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String hello(){
        System.out.println("into hello.........");
        return "{'nihao':'小杨'}";
    }
    @RequestMapping(value = "/queryUserInfoByUserId")
    @ResponseBody
    public UserInfo queryUserInfoByUserId(@RequestParam("userId") String userId){
        System.out.println("into queryUserInfoByUserId.........");
        UserInfo user = null;
        try {
            user = userInfoService.queryUserInfoByUserId(userId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return user;
    }
}
