package com.pinyougou.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.SeckillGoodsMapper;
import com.pinyougou.pojo.SeckillGoods;
import com.pinyougou.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service(interfaceName = "com.pinyougou.seckill.service.SeckillGoodsService")
@Transactional
public class SeckillGoosServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> findSeckillGoods() {
        List<SeckillGoods> seckillGoodsList=null;
        try {
            
            seckillGoodsList = redisTemplate.boundHashOps("seckillGoodsList").values();
            if (seckillGoodsList!=null&&seckillGoodsList.size()>0){
                System.out.println("秒杀商品从redis获取");
                return seckillGoodsList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status","1");
            criteria.andGreaterThan("stockCount","0");
            criteria.andLessThan("startTime",new Date());
            criteria.andGreaterThan("endTime",new Date());
            System.out.println("将秒杀商品存入缓存");
            try {
                for (SeckillGoods seckillGoods : seckillGoodsList) {
                    redisTemplate.boundHashOps("seckillGoodsList")
                            .put(seckillGoods.getId(),seckillGoods);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return seckillGoodsMapper.selectByExample(example);
        } catch (Exception e) {
            System.out.println("查询秒杀商品失败");
            throw new RuntimeException(e);
        }

    }

    @Override
    public SeckillGoods findOneFromRedis(Long id) {

        try {
            return (SeckillGoods) redisTemplate.boundHashOps("seckGoodsList").get(id);
        } catch (Exception e) {
            System.out.println("从Redis读取秒杀商品失败");
            throw new RuntimeException(e);
        }
    }
}
