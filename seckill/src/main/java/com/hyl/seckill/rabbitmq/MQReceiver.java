package com.hyl.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.hyl.seckill.commons.SecKillMessage;
import com.hyl.seckill.entity.OrderInfo;
import com.hyl.seckill.entity.SecKillOrder;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.redis.RedisService;
import com.hyl.seckill.service.GoodsService;
import com.hyl.seckill.service.SecKillOrderService;
import com.hyl.seckill.service.SecKillService;
import com.hyl.seckill.vo.SecKillGoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ 信息接收
 */
@Service
public class MQReceiver {

    private Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SecKillOrderService secKillOrderService;

    @Autowired
    private SecKillService secKillService;

    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void receiverSecKillMessage(String skm){
        System.out.println("Receive Message : " + skm);

        //将接收到的字符串转化为对象
        SecKillMessage secKillMessage = redisService.stringToBean(skm,SecKillMessage.class);

        //1.商品id
        Long goodsId = secKillMessage.getGoodsId();

        //2.秒杀用户
        User user = secKillMessage.getUser();

        //3.获取商品详细信息
        SecKillGoodsVo secKillGoodsVo = goodsService.findSeckGoodsById(goodsId);

        //4.判断是否有库存
        int stock = secKillGoodsVo.getStockCount();
        if(stock <= 0){
            logger.info("库存不足");
            return;
        }

        //5.判断是否秒杀到商品
        SecKillOrder secKillOrder = secKillOrderService.findSecKillOrderInfo(Long.parseLong(user.getId().toString()),goodsId);
        if(secKillOrder != null){
            logger.info("重复秒杀!");
            return;
        }

        //6.减库存、下订单
        secKillService.secKill(user,secKillGoodsVo);

    }

}
