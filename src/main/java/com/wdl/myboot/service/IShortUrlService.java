package com.wdl.myboot.service;

public interface IShortUrlService {
    public void insertShortUrl(int length,int count);
    public String queryUrlByShortUrl(String shortUrl);
}
