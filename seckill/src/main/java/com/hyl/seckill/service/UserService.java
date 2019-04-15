package com.hyl.seckill.service;

import com.hyl.seckill.commons.LoginParam;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.utils.Result;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface UserService {

    User findUserById(Long id);

    Result login(LoginParam loginParam, HttpServletResponse response);
}
