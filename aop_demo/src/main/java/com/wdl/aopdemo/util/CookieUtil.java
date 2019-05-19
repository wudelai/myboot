package com.wdl.aopdemo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static Cookie get(HttpServletRequest request, String token) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie != null && cookie.getName().equals(token)){
                return cookie;
            }
        }
        return null;
    }
}
