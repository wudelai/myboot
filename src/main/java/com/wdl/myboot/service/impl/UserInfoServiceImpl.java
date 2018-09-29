package com.wdl.myboot.service.impl;

import com.wdl.myboot.model.UserInfo;
import com.wdl.myboot.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wdl.myboot.mapper.UserInfoMapper;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo queryUserInfoByUserId(String userId){
        System.out.println("into service......");
      return userInfoMapper.queryUserInfoByUserId(userId);
    }
}
