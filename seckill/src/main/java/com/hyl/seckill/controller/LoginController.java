package com.hyl.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.hyl.seckill.commons.LoginParam;
import com.hyl.seckill.redis.RedisService;
import com.hyl.seckill.service.UserService;
import com.hyl.seckill.utils.CookieUtil;
import com.hyl.seckill.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/to_login")
    public String toLogin(){
        logger.info("Hello！我要去登录页 。。。");
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(HttpServletResponse response, HttpSession session , @Valid LoginParam loginParam){
        logger.info("Login info : {}", JSON.toJSONString(loginParam));
        Result m = userService.login(loginParam,response);
        return m;
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //获取登录的Cookie
        String loginToken = CookieUtil.readCookie(request);

        //删除Cookie
        CookieUtil.delCookie(request,response);

        //删除redis中的key
        redisService.del(loginToken);

        return "redirect:/user/to_login";
    }
}
