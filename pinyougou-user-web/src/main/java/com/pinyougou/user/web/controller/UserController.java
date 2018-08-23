package com.pinyougou.user.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(timeout = 10000)
    private UserService userService;




}
