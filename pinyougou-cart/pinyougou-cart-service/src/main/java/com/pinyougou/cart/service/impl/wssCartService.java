package com.pinyougou.cart.service.impl;

import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Cart;
import com.pinyougou.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class wssCartService  {
    @Autowired
    private ItemMapper itemMapper;


    public List<Cart> addItemToCart(List<Cart> carts, Long itemId, Integer num) {

        Item item = itemMapper.selectByPrimaryKey(itemId);
        String sellerId = item.getSellerId();
        Cart cart=searchCartBySellerId(carts,sellerId);

        return null;
    }

    private Cart searchCartBySellerId(List<Cart> carts, String sellerId) {

        return null;
    }
}
