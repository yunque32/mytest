package com.pinyougou.task;

import com.pinyougou.mapper.SeckillGoodsMapper;
import com.pinyougou.pojo.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public class SeckillTaskService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Scheduled(cron = "0 * * * * ?")
    public void refreshSeckillGoods(){
        System.out.println("执行了任务调度"+new Date());
    }

    @Scheduled(cron = "* * * * * ?")
    public void  removeSeckillGoods(){
        System.out.println("移除秒杀商品任务正在进行....");
        List seckillGoodsList = redisTemplate.boundHashOps("seckillGoodsList").values();
        for (Object o : seckillGoodsList) {
            SeckillGoods seckillGoods = (SeckillGoods) o;
            if (seckillGoods.getEndTime().getTime()<new Date().getTime()){
                seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
                redisTemplate.boundHashOps("seckillGoodsList")
                        .delete(seckillGoods.getId());
                System.out.println("移除秒杀商品:"+seckillGoods.getId());
            }
        }
        System.out.println("移除秒杀商品结束");
    }
}
