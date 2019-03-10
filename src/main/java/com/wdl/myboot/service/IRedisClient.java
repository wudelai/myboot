package com.wdl.myboot.service;

public interface IRedisClient {
    public String get(String key);
    public void set(String key,String value);
}
