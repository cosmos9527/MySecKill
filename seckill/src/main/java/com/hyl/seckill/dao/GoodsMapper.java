package com.hyl.seckill.dao;

import com.hyl.seckill.entity.Goods;
import com.hyl.seckill.vo.SecKillGoodsVo;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> findForList();

    List<SecKillGoodsVo> findAllSecKillGoods();

    SecKillGoodsVo findSecKillGoodsDetail(Long id);
}