package com.pinyougou.user.service;

import com.pinyougou.pojo.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAddressByUserId(String userId);
}
