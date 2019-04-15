package com.hyl.seckill.controller;

import com.hyl.seckill.entity.User;
import com.hyl.seckill.rabbitmq.HelloSender;
import com.hyl.seckill.redis.RedisService;
import com.hyl.seckill.service.UserService;
import com.hyl.seckill.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private HelloSender helloSender;

//    @GetMapping("/findUser")
//    @ResponseBody
//    Result findUser(Long id){
//        User user = (User) redisService.get(id);
//        if(user == null){
//            logger.info("There is no data is redis , get from database!");
//            user = userService.findUserById(id);
//            redisService.set(id.toString(),user);
//        }
//
//        helloSender.send();
//
//        return Result.ok("data",user);
//    }


}
