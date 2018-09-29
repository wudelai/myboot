package com.wdl.myboot.mapper;

import com.wdl.myboot.model.UserInfo;

public interface UserInfoMapper {
    public UserInfo queryUserInfoByUserId(String userId);
}
