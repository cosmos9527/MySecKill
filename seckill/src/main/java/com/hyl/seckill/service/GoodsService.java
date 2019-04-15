package com.hyl.seckill.service;

import com.hyl.seckill.entity.Goods;
import com.hyl.seckill.vo.SecKillGoodsVo;

import java.util.List;

public interface GoodsService {

    List<Goods> findGoodsList();

    List<SecKillGoodsVo> findAllSecKillGoods();

    SecKillGoodsVo findSeckGoodsById(Long id);
}
