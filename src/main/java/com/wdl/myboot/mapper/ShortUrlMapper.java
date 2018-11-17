package com.wdl.myboot.mapper;

import com.wdl.myboot.model.ShortUrlModel;

public interface ShortUrlMapper {
    void insertShortUrl(ShortUrlModel shortUrlModel);
    String queryShortUrl(String shortUrl);
}
