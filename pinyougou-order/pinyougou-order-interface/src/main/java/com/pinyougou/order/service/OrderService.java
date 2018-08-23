package com.pinyougou.order.service;

import com.pinyougou.pojo.Order;
import com.pinyougou.pojo.PayLog;

public interface OrderService {

    void saveOrder(Order order);

    PayLog findPayLogFromRedis(String userId);

    void updateOrderStatus(String outTradeNo,String transactionId);

}
