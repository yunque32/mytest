package com.pinyougou.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.common.Util.IdWorker;
import com.pinyougou.mapper.OrderMapper;
import com.pinyougou.mapper.PayLogMapper;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pojo.Cart;
import com.pinyougou.pojo.Order;
import com.pinyougou.pojo.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(interfaceName = "com.pinyougou.order.service.OrderService")
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PayLogMapper payLogMapper;

    @Override
    public void saveOrder(Order order) {
        List<Cart> carts = (List<Cart>) redisTemplate.boundValueOps("cart_" + order.getUserId()).get();
        List<String> orderList=new ArrayList<>();
        double totalMoney=0;

        for (Cart cart : carts) {
            Order order1 = new Order();
            Long orderId = idWorker.nextId();
            order1.setOrderId(orderId);
            order1.setPaymentType(order.getPaymentType());
            order1.setStatus("1");
            order1.setCreateTime(new Date());
            order1.setUpdateTime(order.getUpdateTime());
            order1.setUserId(order.getUserId());
            order1.setReceiverAreaName(order.getReceiverAreaName());
            order1.setReceiverMobile(order.getReceiverMobile());
            order1.setReceiver(order.getReceiver());
            order1.setSourceType(order.getSourceType());
            double money=0.0;








        }
    }

    @Override
    public PayLog findPayLogFromRedis(String userId) {
        return null;
    }

    @Override
    public void updateOrderStatus(String outTradeNo, String transactionId) {

    }
}
