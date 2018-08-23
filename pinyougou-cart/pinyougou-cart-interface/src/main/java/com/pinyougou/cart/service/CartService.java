package com.pinyougou.cart.service;

import com.pinyougou.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> addItemToCart(List<Cart> carts, Long itemId, Integer num);
}
