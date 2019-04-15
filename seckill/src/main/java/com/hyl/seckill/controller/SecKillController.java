package com.hyl.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.hyl.seckill.commons.Const;
import com.hyl.seckill.commons.SecKillMessage;
import com.hyl.seckill.entity.OrderInfo;
import com.hyl.seckill.entity.SecKillOrder;
import com.hyl.seckill.entity.User;
import com.hyl.seckill.rabbitmq.MQSender;
import com.hyl.seckill.redis.RedisService;
import com.hyl.seckill.service.GoodsService;
import com.hyl.seckill.service.SecKillOrderService;
import com.hyl.seckill.utils.CookieUtil;
import com.hyl.seckill.utils.RequestUtil;
import com.hyl.seckill.utils.SerializeUtil;
import com.hyl.seckill.vo.SecKillGoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {


    private Logger logger = LoggerFactory.getLogger(SecKillController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SecKillOrderService secKillOrderService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;


    private Map<Long,Boolean> localOverMap = new HashMap<>();

    /**
     * 把秒杀商品库存放到redis中
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<SecKillGoodsVo> goodsVoList = goodsService.findAllSecKillGoods();
        if(goodsVoList == null){
            return;
        }
        for (SecKillGoodsVo sgoods : goodsVoList) {
            logger.info("把商品库存放到redis中：{}",JSON.toJSONString(sgoods));
            redisService.set(Const.GOODS_PREFIX_KEY + sgoods.getGoodsId(),sgoods.getStockCount(),60 * 60 * 24 * 30);
            localOverMap.put(sgoods.getGoodsId(),false);
        }
    }

    @RequestMapping("/seckill")
    @ResponseBody
    public String deSecKill(Long goodsId, HttpServletRequest request, HttpServletResponse response) throws Exception {


        System.out.println("秒杀商品 : " + goodsId);

        //RequestUtil.printParam(request);

        //拿到登录用户的cookie值
        //String loginToken = CookieUtil.readCookie(request);
        //测试：直接从request中拿到token值
        String loginToken = request.getParameter("token");
        //System.out.println("cookie value : " + loginToken);

        //从redis中获取登录用户
        User loginUser = redisService.get(loginToken,User.class);

        //判断用户是否登录
        if(loginUser == null){
            logger.info("User does not login ！to login page ");
            return "redirect:/user/to_login";
        }

        //1.内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over){
            //request.setAttribute("error","库存不足！");
            logger.info("localOverMap no stock!");
            return "库存不足！";
        }

        //2.预减库存
        Long stock = redisService.decr(Const.GOODS_PREFIX_KEY+goodsId);
        if(stock < 0){
            localOverMap.put(goodsId,true);
            //request.setAttribute("error","库存不足！");
            logger.info("redisService no stock!");
            return "库存不足！";
        }

        //判断是否已经秒杀到商品
        SecKillOrder secKillOrder = secKillOrderService.findSecKillOrderInfo(Long.parseLong(loginUser.getId().toString()),goodsId);
        if(secKillOrder != null){
            //request.setAttribute("error","");
            logger.info("repeat buy!");
            return "重复秒杀!";
        }

        //入队
        SecKillMessage skm = new SecKillMessage();
        skm.setGoodsId(goodsId);
        skm.setUser(loginUser);
        mqSender.send(skm);


        //1.获取秒杀商品详情
//        SecKillGoodsVo goods = goodsService.findSeckGoodsById(goodsId);

//        //2.查看库存是否充足
//        int stock = goods.getStockCount();

//        //库存不足
//        if(stock <= 0){
//            request.setAttribute("error","库存不足,秒杀失败！");
//            return "500";
//        }



        //下订单
//        OrderInfo orderInfo = secKillOrderService.insert(loginUser,goods);
//
//        request.setAttribute("orderInfo",orderInfo);
//        request.setAttribute("goods",goods);
        return "正在秒杀队列中！";
    }




}
