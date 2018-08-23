package com.pinyougou.user.service;

import com.pinyougou.pojo.User;

public interface UserService {

    void save(User user);
    void sendSmsCode(String phone);
    boolean checkSmsCode(String phone,String smsCode);

}
