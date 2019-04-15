package com.hyl.seckill.dao;

import com.hyl.seckill.entity.OrderInfo;
import com.hyl.seckill.entity.SecKillOrder;
import org.apache.ibatis.annotations.Param;

public interface SecKillOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecKillOrder record);

    int insertSelective(SecKillOrder record);

    SecKillOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecKillOrder record);

    int updateByPrimaryKey(SecKillOrder record);

    SecKillOrder findSecKillOrder(@Param("userId") Long userId, @Param("goodsId") Long goodsId);
}