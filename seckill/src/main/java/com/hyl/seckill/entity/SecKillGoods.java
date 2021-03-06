package com.hyl.seckill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SecKillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long goodsId;

    private BigDecimal seckilPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getSeckilPrice() {
        return seckilPrice;
    }

    public void setSeckilPrice(BigDecimal seckilPrice) {
        this.seckilPrice = seckilPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}