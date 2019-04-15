package com.hyl.seckill.commons;

import com.hyl.seckill.entity.User;

/**
 * 秒杀信息
 */
public class SecKillMessage {

    private Long goodsId; //秒杀商品ID

    private User user;  //秒杀用户

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
