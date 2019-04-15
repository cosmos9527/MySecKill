package com.hyl.seckill.service;

import com.hyl.seckill.entity.Goods;
import com.hyl.seckill.entity.OrderInfo;
import com.hyl.seckill.entity.SecKillOrder;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.vo.SecKillGoodsVo;

public interface SecKillOrderService {

    SecKillOrder findSecKillOrderInfo(Long userId, Long goodsId);

    OrderInfo insert(User user, SecKillGoodsVo goods);

    void secKill(User user,SecKillGoodsVo secKillGoodsVo);
}
