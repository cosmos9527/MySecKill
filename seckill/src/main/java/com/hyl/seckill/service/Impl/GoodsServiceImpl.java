package com.hyl.seckill.service.Impl;

import com.hyl.seckill.dao.GoodsMapper;
import com.hyl.seckill.entity.Goods;
import com.hyl.seckill.service.GoodsService;
import com.hyl.seckill.vo.SecKillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> findGoodsList() {
        return goodsMapper.findForList();
    }

    @Override
    public List<SecKillGoodsVo> findAllSecKillGoods() {
        return goodsMapper.findAllSecKillGoods();
    }

    @Override
    public SecKillGoodsVo findSeckGoodsById(Long id) {
        return goodsMapper.findSecKillGoodsDetail(id);
    }
}
