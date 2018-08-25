package cn.itcast.casclient.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2018-08-01<p>
 */
@RestController
public class LoginController {

    /** 获取登录用户名 */
    @GetMapping("/user")
    public String showName(){
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return username;
    }
}
