package com.wdl.myboot.service;

import com.wdl.myboot.model.UserInfo;

public interface IUserInfoService {
    UserInfo queryUserInfoByUserId(String userId) throws Exception;
}
