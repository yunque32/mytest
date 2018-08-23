package com.pinyougou.manager.controller;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.sellergoods.service.GoodsService;

import javax.jms.Destination;


@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;

	@Autowired
    private Destination solrQueue;
	@Autowired
    private  Destination solrDeleteQueue;
	@Autowired
    private Destination pageTopic;
	@Autowired
    private Destination pageDeleteTopic;

	@GetMapping("/findByPage")
	public PageResult findByPage(Goods goods,Integer page,Integer rows){
        return null;
    }

}
