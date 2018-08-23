package com.pinyougou.seckill.service;

import com.pinyougou.pojo.SeckillGoods;

import java.util.List;

public interface SeckillGoodsService {
    List<SeckillGoods> findSeckillGoods();

    SeckillGoods findOneFromRedis(Long id);
}
