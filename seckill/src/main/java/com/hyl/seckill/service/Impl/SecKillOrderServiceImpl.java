package com.hyl.seckill.service.Impl;

import com.hyl.seckill.dao.OrderInfoMapper;
import com.hyl.seckill.dao.SecKillGoodsMapper;
import com.hyl.seckill.dao.SecKillOrderMapper;
import com.hyl.seckill.entity.Goods;
import com.hyl.seckill.entity.OrderInfo;
import com.hyl.seckill.entity.SecKillOrder;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.service.SecKillOrderService;
import com.hyl.seckill.vo.SecKillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.util.Date;

@Service
public class SecKillOrderServiceImpl implements SecKillOrderService {

    @Autowired
    private SecKillOrderMapper secKillOrderMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private SecKillGoodsMapper secKillGoodsMapper;

    @Override
    public SecKillOrder findSecKillOrderInfo(Long userId, Long goodsId) {
        return secKillOrderMapper.findSecKillOrder(userId,goodsId);
    }

    @Override
    public OrderInfo insert(User user, SecKillGoodsVo goods) {

        //秒杀商品库存减1
        secKillGoodsMapper.reduceStockCount(goods.getGoodsId());
        //生成订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(goods.getGoodsId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getGoodsPrice());
        orderInfo.setCreateDate(new Date());
        orderInfo.setUserId(Long.parseLong(user.getId().toString()));
        orderInfo.setStatus(0);
        orderInfo.setGoodsCount(1);
        orderInfo.setOrderChannel(1);
        orderInfo.setAddrId(10L);

        //加入订单列表
        //Integer orderId = orderInfoMapper.insert(orderInfo);
        //创建秒杀订单
        SecKillOrder secKillOrder = new SecKillOrder();
        secKillOrder.setGoodsId(goods.getGoodsId());
        secKillOrder.setOrderId(orderInfo.getId());
        secKillOrder.setUserId(Long.parseLong(user.getId().toString()));
        //插入秒杀商品表
        secKillOrderMapper.insert(secKillOrder);
        return orderInfo;
    }

    @Transactional
    @Override
    public void secKill(User user, SecKillGoodsVo secKillGoodsVo) {

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
    }
}
