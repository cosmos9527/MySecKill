package com.hyl.seckill.service.Impl;

import com.hyl.seckill.commons.LoginParam;
import com.hyl.seckill.dao.UserMapper;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.redis.RedisService;
import com.hyl.seckill.service.UserService;
import com.hyl.seckill.utils.CookieUtil;
import com.hyl.seckill.utils.MD5Utils;
import com.hyl.seckill.utils.Result;
import com.hyl.seckill.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;


    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public Result login(LoginParam loginParam, HttpServletResponse response) {

        String mobile = loginParam.getMobile();
        String password = loginParam.getPassword();

        User user = userMapper.findUserByPhone(mobile);

        if(user == null){
           return Result.error("用户不存在!");
        }

        String dbSalt = user.getSalt();
        String dbPass = user.getPassword();
        String md5Pass = MD5Utils.formPass2DbPass(password,dbSalt);

        if(!md5Pass.equals(dbPass)){
            return Result.error("账号或密码错误!");
        }

        String token = UUIDUtil.uuid();
        redisService.set(token,user,60 * 60 * 60 * 24);
        CookieUtil.writeLoginToken(response,token);

        return Result.ok("data",token);
    }
}
