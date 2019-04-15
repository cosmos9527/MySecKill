package com.hyl.seckill.dao;

import com.hyl.seckill.entity.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}