package com.pinyougou.sellergoods.service;


import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.Item;

import java.util.List;
import java.util.Map;

public interface GoodsService {

    void saveGoods(Goods goods);

    PageResult findGoodsByPage(Goods goods, Integer page, Integer rows);

    Goods findOne(Long id);

    void updateGoods(Goods goods);

    void updateAuditStatus(Long[] ids,String status);

    void updateDeleteStatus(Long[] ids);

    List<Item> findItemByGoodsIdAndStatus(Long[] longs, String status);

    Map<String,Object> getItem(Long goodsId);
}
