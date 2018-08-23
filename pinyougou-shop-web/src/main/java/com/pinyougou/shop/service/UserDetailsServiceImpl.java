package com.pinyougou.shop.service;

import com.pinyougou.pojo.Seller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("sellerService: " + sellerService);
        // 根据商家的id查询商家
        Seller seller = sellerService.findOne(username);
        // 判断商家是否登录成功
        if (seller != null && seller.getStatus().equals("1")) {

            // 创建List集合封装角色与权限
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 添加权限与角色数据
            authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

            // 返用用户 (密码由SpringSecurity判断)
            return new User(username, seller.getPassword(), authorities);
        }
        return null;
    }

    public SellerService getSellerService() {
        return sellerService;
    }
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }
}
