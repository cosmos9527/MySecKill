package com.hyl.seckill.task;

import com.hyl.seckill.utils.HttpUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/4/12
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class Task implements Runnable {

    private Long goodsId;
    private String token;

    @Override
    public void run() {

        // 拼接请求url和请求参数
        String url = "http://localhost:8888/seckill/seckill";
        String params = "goodsId=" + goodsId + "&" + "token=" + token;

        // 发送秒杀请求
        String result = HttpUtil.sendGet(url,params);
        System.out.println("请求结果:" + result);
    }

    public Task(Long goodsId, String token) {
        this.goodsId = goodsId;
        this.token = token;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
