package com.pinyougou.cart.controller;

import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.common.cookie.CookieUtils;
import com.pinyougou.pojo.Cart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cartwss")
public class WSScart {

    @Autowired
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    public boolean addCart(Long itemId,Integer num){
        try {
            List<Cart> cartList = findCart();
            cartList = cartService.addItemToCart(cartList, itemId, num);

            return true;
        }catch (Exception e){
            System.out.println("添加购物车失败");
        }


     return false;
    };

    public List<Cart> findCart(){
        String cookieValue = CookieUtils.getCookieValue(request,
                CookieUtils.CookieName.PINYOUGOU_CART, true);
        if (StringUtils.isBlank(cookieValue)){
            cookieValue="[]";
        }
       return   JSON.parseArray(cookieValue, Cart.class);
    }



}
