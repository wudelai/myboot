package com.wdl.myboot.service.impl;

import com.wdl.myboot.mapper.ShortUrlMapper;
import com.wdl.myboot.model.ShortUrlModel;
import com.wdl.myboot.service.IShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service("shortUrlService")
public class ShortUrlServiceImpl implements IShortUrlService {
    @Autowired
    private ShortUrlMapper shortUrlMapper;

    public static char[] VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    private Random random  = new Random(System.currentTimeMillis());;

    private int length = 4;

    private static final String homeUrl = "http://localhost:8089/share/a/";
    @Override
    public void insertShortUrl(int length,int count) {
        for (int i=0;i<= count;i++){
            Map<String,Object> result = new HashMap<String,Object>();
            this.length = length;
            String oldUrl = "https://dmzstg1.pa18.com/ebusiness/online/index.html?mediaSource%3DSC03-XXTB-DRICET-GFWX&params="+i;
            String shortUrl = this.generate(oldUrl);
            ShortUrlModel sm = new ShortUrlModel();
            sm.setOldUrl(oldUrl);
            sm.setShortUrl(shortUrl);
            try {
                shortUrlMapper.insertShortUrl(sm);
            }catch(Exception e){
                result.put("errorCode","999");
            }
        }


    }

    @Override
    public String queryUrlByShortUrl(String shortUrl) {
        return seek(shortUrl);
    }

    public String generate(String url, int seed) {
        char[] sortUrl = new char[length];
        for (int i = 0; i < length; i++) {
            sortUrl[i] = VALID_CHARS[seed % VALID_CHARS.length];
            seed = random.nextInt(Integer.MAX_VALUE) % VALID_CHARS.length;
        }
        return new String(sortUrl);
    }

    public String generate(String url) {
        String shortUrl = generate(url, random.nextInt(Integer.MAX_VALUE));
        while (seek(shortUrl) != null) {
            System.out.println("这里有一个存在的***************************");
            shortUrl = generate(url, random.nextInt(Integer.MAX_VALUE));
        }
        return shortUrl;
    }
    public String seek(String shortUrl) {
        return shortUrlMapper.queryShortUrl(shortUrl);
    }
}
