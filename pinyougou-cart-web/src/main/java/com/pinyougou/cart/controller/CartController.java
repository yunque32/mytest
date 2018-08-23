package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.common.cookie.CookieUtils;
import com.pinyougou.pojo.Cart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 10000)
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @GetMapping("/addCart")
    @CrossOrigin(origins = "http://item.pinyougou.com",allowCredentials = "true")
    public boolean addCart(Long itemId,Integer num){
        try {
            List<Cart> carts=findCart();
            carts=cartService.addItemToCart(carts,itemId,num);
            CookieUtils.setCookie(request,response,CookieUtils.CookieName.PINYOUGOU_CART,
                    JSON.toJSONString(carts),3600*24,true);

            response.setHeader("Access-Control-Allow-Origin","http://item.pinyougou.com");
            response.setHeader("Access-Control-Allow-Credentials","true");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/findCart")
    public List<Cart> findCart(){
        String cartStr = CookieUtils.getCookieValue(request,
                CookieUtils.CookieName.PINYOUGOU_CART, true);
        if (StringUtils.isBlank(cartStr)){
            cartStr="[]";

        }
        List<Cart> carts = JSON.parseArray(cartStr, Cart.class);

        return carts;

     }
}
