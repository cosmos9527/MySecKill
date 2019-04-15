package com.hyl.seckill.service;

import com.hyl.seckill.entity.OrderInfo;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.vo.SecKillGoodsVo;

public interface SecKillService {

    OrderInfo secKill(User user, SecKillGoodsVo goodsVo);



}
