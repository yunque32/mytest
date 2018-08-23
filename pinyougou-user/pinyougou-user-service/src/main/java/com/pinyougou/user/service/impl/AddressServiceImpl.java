package com.pinyougou.user.service.impl;

import com.pinyougou.mapper.AddressMapper;
import com.pinyougou.pojo.Address;
import com.pinyougou.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Override
    public List<Address> findAddressByUserId(String userId) {
        Address address = new Address();
        address.setUserId(userId);
        return addressMapper.select(address);
    }
}
