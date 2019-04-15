package com.hyl.seckill.rabbitmq;

import com.hyl.seckill.commons.SecKillMessage;
import com.hyl.seckill.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ 信息发送
 */
@Service
public class MQSender {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(SecKillMessage skm){
        String msg = redisService.beanToString(skm);
        System.out.println("send message : " + msg);

        amqpTemplate.convertAndSend(RabbitMQConfig.SECKILL_QUEUE,msg);
    }

}
