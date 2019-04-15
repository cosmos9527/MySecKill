package com.hyl.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.hyl.seckill.entity.Goods;
import com.hyl.seckill.service.GoodsService;
import com.hyl.seckill.vo.SecKillGoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public String list(Model model){
        List<SecKillGoodsVo> goodsList = goodsService.findAllSecKillGoods();
        model.addAttribute("goodsList",goodsList);
        logger.info("SecKill Goods : {}", JSON.toJSONString(goodsList));
        return "goods_list";
    }

    @RequestMapping(value = "/detail/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String toDetail(Model model, @PathVariable("id") Long id){
        logger.info("Go in goods detail page!");
        SecKillGoodsVo secKillGoodsVo = goodsService.findSeckGoodsById(id);

        if(secKillGoodsVo == null){
            return "404";
        }else {
            model.addAttribute("goods",secKillGoodsVo);

            Long start = secKillGoodsVo.getStartDate().getTime();
            Long end = secKillGoodsVo.getEndDate().getTime();
            Long now = System.currentTimeMillis();

            int status = 0;
            int remainSeconds = 0;
            if(now < start){
                //活动未开始
                status = 0;
                remainSeconds = (int)((start - now )/1000);
            }else if(now < end){
                //活动正在进行中
                status = 1;
                remainSeconds = 0;
            }else {
                //活动已结束
                status = 2;
                remainSeconds = -1;
            }
            model.addAttribute("seckillStatus",status);
            model.addAttribute("remainSeconds",remainSeconds);
        }



        logger.info("Goods Detail : {}",JSON.toJSONString(secKillGoodsVo));
        return "goods_detail";
    }
}
