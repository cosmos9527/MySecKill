package com.hyl.seckill.service.Impl;

import com.hyl.seckill.dao.OrderInfoMapper;
import com.hyl.seckill.dao.SecKillGoodsMapper;
import com.hyl.seckill.dao.SecKillOrderMapper;
import com.hyl.seckill.entity.OrderInfo;
import com.hyl.seckill.entity.SecKillOrder;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.service.SecKillService;
import com.hyl.seckill.vo.SecKillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    private SecKillGoodsMapper secKillGoodsMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private SecKillOrderMapper secKillOrderMapper;

    @Override
    public OrderInfo secKill(User user, SecKillGoodsVo secKillGoodsVo) {

        //秒杀商品库存减1
        secKillGoodsMapper.reduceStockCount(secKillGoodsVo.getGoodsId());

        //生成订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddrId(100100L);
        orderInfo.setOrderChannel(1);
        orderInfo.setGoodsCount(secKillGoodsVo.getGoodsStock());
        orderInfo.setStatus(0);
        orderInfo.setUserId(Long.parseLong(user.getId().toString()));
        orderInfo.setGoodsPrice(secKillGoodsVo.getGoodsPrice());
        orderInfo.setGoodsName(secKillGoodsVo.getGoodsName());
        orderInfo.setGoodsId(secKillGoodsVo.getGoodsId());
        orderInfo.setCreateDate(new Date());

        //写入订单
        orderInfoMapper.insert(orderInfo);

        //创建秒杀订单
        SecKillOrder secKillOrder = new SecKillOrder();
        secKillOrder.setUserId(Long.parseLong(user.getId().toString()));
        secKillOrder.setOrderId(orderInfo.getId());
        secKillOrder.setGoodsId(secKillGoodsVo.getGoodsId());

        //写入订单
        secKillOrderMapper.insert(secKillOrder);

        return null;
    }
}
