package com.hyl.seckill.utils;

import java.util.HashMap;

public class Result extends HashMap {

    public static Result ok(){
        Result res = new Result();
        res.put("code",0);
        res.put("msg","ok");
        return res;
    }

    public static Result ok(String key,Object value){
        Result res = new Result();
        res.put("code",0);
        res.put("msg","ok");
        res.put(key,value);
        return res;
    }

    public static Result ok(String msg){
        Result res = new Result();
        res.put("code",0);
        res.put("msg",msg);
        return res;
    }

    public static Result ok(String msg,String key,Object value){
        Result res = new Result();
        res.put("code",0);
        res.put("msg",msg);
        res.put(key,value);
        return res;
    }

    public static Result ok(int code,String msg){
        Result res = new Result();
        res.put("code",code);
        res.put("msg",msg);
        return res;
    }

    public static Result ok(int code,String msg,String key,Object value){
        Result res = new Result();
        res.put("code",code);
        res.put("msg",msg);
        res.put(key,value);
        return res;
    }

    public static Result error(String msg){
        Result res = new Result();
        res.put("code",500);
        res.put("msg",msg);
        return res;
    }

    public static Result error(int code,String msg){
        Result res = new Result();
        res.put("code",code);
        res.put("msg",msg);
        return res;
    }
}
