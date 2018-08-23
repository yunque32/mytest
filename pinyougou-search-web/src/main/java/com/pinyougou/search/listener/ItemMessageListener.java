package com.pinyougou.search.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.Item;
import com.pinyougou.search.service.ItemSearchService;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.solr.SolrItem;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemMessageListener implements SessionAwareMessageListener<MapMessage> {

    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @Reference(timeout = 30000)
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(MapMessage mapMessage, Session session) throws JMSException {
        System.out.println("----ItemMessageListener------");
        List<Long> ids = (List<Long>) mapMessage.getObject("ids");
        String status = mapMessage.getString("status");
        System.out.println("ids:"+ids);
        System.out.println("status:"+status);

        List<Item> itemList=goodsService.findItemByGoodsIdAndStatus(ids.toArray(new Long[ids.size()]),status);
        List<SolrItem> solrItemslist = new ArrayList<>();


        itemSearchService.saveOrUpdate(solrItemslist);
    }
}
