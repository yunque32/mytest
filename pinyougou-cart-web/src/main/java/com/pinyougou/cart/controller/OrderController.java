package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pojo.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference(timeout = 10000)
    private OrderService orderService;

    @PostMapping("/save")
    public boolean saveOrder(@RequestBody Order order, HttpServletRequest request){

        try {
            String userId = request.getRemoteUser();
            order.setUserId(userId);
            orderService.saveOrder(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}
