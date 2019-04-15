package com.hyl.seckill.utils;

import com.alibaba.druid.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static final String COOKIE_DOMAIN = "localhost";
    private static final String COOKIE_NAME = "seckill_login_token";

    /**
     * 读取Cookie
     * @param request
     * @return
     */
    public static String readCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie: cookies) {
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 写入Cookie
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/"); //代表设置在根目录
        cookie.setMaxAge(60 * 60 * 24 * 30); //不设置默认存在内存中不写入磁盘
        response.addCookie(cookie);
    }

    /**
     * 删除Cookie
     * @param request
     * @param response
     */
    public static void delCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(StringUtils.equals(COOKIE_NAME,cookie.getName())){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0); //设置时间为0，表示删除此cookie
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
}
