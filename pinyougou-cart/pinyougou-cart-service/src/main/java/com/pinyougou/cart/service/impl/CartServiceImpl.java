package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Cart;
import com.pinyougou.pojo.Item;
import com.pinyougou.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(interfaceName = "com.pinyougou.cart.service.CartService")
@Transactional
public class CartServiceImpl implements CartService{

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Cart> addItemToCart(List<Cart> carts, Long itemId, Integer num) {
        try {
            Item item = itemMapper.selectByPrimaryKey(itemId);
            String sellerId = item.getSellerId();
            Cart cart = searchCartBySellerId(carts, sellerId);
            if (cart==null){
                cart = new Cart();
                cart.setSellerId(sellerId);
                cart.setSellerName(item.getSeller());

                OrderItem orderItem = createOrderItem(item, num);
                List<OrderItem> orderItems = new ArrayList<>(0);
                orderItems.add(orderItem);
                cart.setOrderItems(orderItems);
            }else {
                OrderItem orderItem = searchOrderItemByItemId(cart.getOrderItems(), itemId);
                if (orderItem==null){
                    orderItem=createOrderItem(item, num);
                    cart.getOrderItems().add(orderItem);
                }else {
                    orderItem.setNum(orderItem.getNum() + num);
                    orderItem.setTotalFee(new BigDecimal(
                            orderItem.getPrice().doubleValue()
                                    * orderItem.getNum()));
                    // 如果订单明细的购买数小于等于0，则删除
                    if (orderItem.getNum() <= 0){
                        // 删除购物车中的订单明细(商品)
                        cart.getOrderItems().remove(orderItem);
                    }
                    // 如果cart的orderItems订单明细为0，则删除cart
                    if (cart.getOrderItems().size() == 0){
                        carts.remove(cart);
                    }

                }
            }
            return carts;
        } catch (Exception e) {
            System.out.println("增加购物车失败");
            throw new RuntimeException(e);
        }
    }

    private Cart searchCartBySellerId(List<Cart> carts,String sellerId){
        for (Cart cart : carts) {
            if (cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }

    private OrderItem createOrderItem(Item item,Integer num){
        OrderItem orderItem = new OrderItem();
        orderItem.setSellerId(item.getSellerId());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
        return  orderItem;
    }

    private OrderItem searchOrderItemByItemId(List<OrderItem> orderItems,Long itemId){
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItemId().equals(itemId)){
                return orderItem;
            }
        }
        return null;
    }
}
