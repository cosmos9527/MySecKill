package com.hyl.seckill.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;

@Configuration
public class RabbitMQConfig {

    public static final String SECKILL_QUEUE = "seckill";

    @Bean
    public Queue Queue(){
        return new Queue("hello");
    }

    @Bean
    public Queue sQueue(){
        return new Queue(SECKILL_QUEUE,true);
    }
}
