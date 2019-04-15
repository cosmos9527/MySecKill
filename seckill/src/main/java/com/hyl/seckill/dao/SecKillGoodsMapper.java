package com.hyl.seckill.dao;

import com.hyl.seckill.entity.SecKillGoods;

public interface SecKillGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecKillGoods record);

    int insertSelective(SecKillGoods record);

    SecKillGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecKillGoods record);

    int updateByPrimaryKey(SecKillGoods record);

    void reduceStockCount(Long goodsId);

}