package com.hyl.seckill.redis;

import com.alibaba.fastjson.JSON;
import com.hyl.seckill.commons.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {


    @Autowired
    JedisPool jedisPool;

    /**
     * 获取当个对象
     * */
    public <T> T get(String key,  Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String  str = jedis.get(key);
            T t =  stringToBean(str, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public  Long expice(String key,int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis =  jedisPool.getResource();
            result = jedis.expire(key,exTime);
            return result;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     * */
    public <T> boolean set(String key,  T value ,int exTime) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            //生成真正的key
            if(exTime == 0) {
                //直接保存
                jedis.set(key, str);
            }else {
                //设置过期时间
                jedis.setex(key, exTime, str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }
    public  Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis =  jedisPool.getResource();
            result = jedis.del(key);
            return result;
        } finally {
            returnToPool(jedis);
        }
    }
    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            return  jedis.exists(key);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * */
    public <T> Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            return  jedis.incr(key);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            return  jedis.decr(key);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * bean 转 String
     * @param value
     * @param <T>
     * @return
     */
    public  <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }


    /**
     * string转bean
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public  <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }


//    @Autowired
//    private RedisTemplate<String,Object> redisTemplate;
//
//    @Autowired
//    private ValueOperations valueOperations;
//
//    /**
//     * 获取单个对象
//     * @param key
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public <T> T get(String key,Class<T> clazz){
//
//        try {
//
//            //获取对象字符串
//            String value = (String) get(key);
//            //转化成对象
//            T t = stringToBean(value,clazz);
//            return t;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public Object get(String key){
//        return valueOperations.get(key);
//    }
//
//    public void set(String key,Object value){
//        valueOperations.set(key,value);
//    }
//
//    /**
//     * 删除 key
//     * @param key
//     */
//    public void  delKey(String key){
//        redisTemplate.delete(key);
//    }
//
//    /**
//     * 保存对象到redis中，并设置过期时间
//     * @param key 键
//     * @param value 值
//     * @param expire_time 保存时间
//     * @param <T>
//     * @return
//     */
//    public <T> boolean set(String key,T value,int expire_time){
//
//        if(value == null){
//            return  false;
//        }
//
//        String valStr = JSON.toJSONString(value);
//
//        if(expire_time == 0){
//            //直接保存
//            redisTemplate.opsForValue().set(key,valStr);
//        }else{
//            //设置过期时间
//            redisTemplate.opsForValue().set(key,valStr,expire_time, TimeUnit.SECONDS);
//        }
//
//        return true;
//    }
//
//    /**
//     * string转bean
//     * @param str
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public <T> T stringToBean(String str, Class<T> clazz) {
//        if(str == null || str.length() <= 0 || clazz == null) {
//            return null;
//        }
//        if(clazz == int.class || clazz == Integer.class) {
//            return (T)Integer.valueOf(str);
//        }else if(clazz == String.class) {
//            return (T)str;
//        }else if(clazz == long.class || clazz == Long.class) {
//            return  (T)Long.valueOf(str);
//        }else {
//            return JSON.toJavaObject(JSON.parseObject(str), clazz);
//        }
//    }
//
//    /**
//     * bean 转 String
//     * @param value
//     * @param <T>
//     * @return
//     */
//    public  <T> String beanToString(T value) {
//        if(value == null) {
//            return null;
//        }
//        Class<?> clazz = value.getClass();
//        if(clazz == int.class || clazz == Integer.class) {
//            return ""+value;
//        }else if(clazz == String.class) {
//            return (String)value;
//        }else if(clazz == long.class || clazz == Long.class) {
//            return ""+value;
//        }else {
//            return JSON.toJSONString(value);
//        }
//    }
//
//    private void returnToPool(Jedis jedis) {
//        if(jedis != null) {
//            jedis.close();
//        }
//    }
//
//    /**
//     * 预减秒杀商品库存
//     * @param goodsId
//     * @return
//     */
//    public Integer decr(String goodsId){
//
//        //获取对应商品库存
//        Integer stock = (Integer) get(Const.GOODS_PREFIX_KEY+goodsId);
//        //减库存
//        stock -= stock;
//        set(Const.GOODS_PREFIX_KEY+goodsId,stock);
//        return stock;
//    }



}
