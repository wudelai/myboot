package com.wdl.myboot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShorUrlTest {
    public static char[] VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private static Random random = new Random(System.currentTimeMillis());
    protected int length = 4;

    private Map<String, String> url2ShortUrl = new ConcurrentHashMap<String, String>();
    private Map<String, String> shortUrl2Url = new ConcurrentHashMap<String, String>();

    public ShorUrlTest() {

    }

    public ShorUrlTest(int length) {
        this.length = length;
    }


    public String generate(String url, int seed) {
        char[] sortUrl = new char[length];
        for (int i = 0; i < length; i++) {
            sortUrl[i] = VALID_CHARS[seed % VALID_CHARS.length];
            seed = random.nextInt(Integer.MAX_VALUE) % VALID_CHARS.length;
        }
//        url2ShortUrlTree.add(new String(sortUrl));
        return new String(sortUrl);
    }

    public String generate(String url) {
        String shortUrl;
        shortUrl = generate(url, random.nextInt(Integer.MAX_VALUE));
        while (seek(shortUrl) != null) {
            System.out.println("这里有一个存在的***************************");
            shortUrl = generate(url, random.nextInt(Integer.MAX_VALUE));
        }
        put(url, shortUrl);
        return shortUrl;
    }

    public String get(String url){
        String sortUrl = seek(url);
        if (sortUrl == null) {
            sortUrl = generate(url);
            put(url, sortUrl);
        }
        return sortUrl;
    }

    public void put(String url, String shortUrl) {
        url2ShortUrl.put(url, shortUrl);
        shortUrl2Url.put(shortUrl, url);
    }

    public String seek(String shortUrl) {
        return shortUrl2Url.get(shortUrl);
    }

    public void clean(String url) {
        String sortUrl = url2ShortUrl.get(url);
        if (sortUrl != null) {
            url2ShortUrl.remove(url);
            shortUrl2Url.remove(sortUrl);
        }
    }


    public static void main(String[] args) {
        int j =9;
//        for(int j=1;j<10;j++) {
            ShorUrlTest urlShortener = new ShorUrlTest(j);
            for (int i = 0; i < 1000; i++) {
                System.out.println(urlShortener.get("http://www.tinygroup.org"));
            }
            System.out.println();
//        }


    }


}
