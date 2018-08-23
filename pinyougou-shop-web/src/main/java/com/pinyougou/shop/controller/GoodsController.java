package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference(timeout = 10000)
	private GoodsService goodsService;

	@PostMapping("/save")
	public boolean save(@RequestBody Goods goods){
		try{
			String sellerId = SecurityContextHolder.getContext()
					.getAuthentication().getName();
			goods.setSellerId(sellerId);
			goodsService.saveGoods(goods);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/** 多条件分页查询商品 */
	@GetMapping("/findByPage")
	public PageResult findByPage(Goods goods, Integer page, Integer rows){
		try{

			// Get请求中文转码
			if (goods != null && StringUtils.isNoneBlank(goods.getGoodsName())){
				goods.setGoodsName(new String(goods.getGoodsName()
						.getBytes("ISO8859-1"), "UTF-8"));
			}
			// 获取登录的用户名(商家ID)
			String sellerId = SecurityContextHolder.getContext()
					.getAuthentication().getName();
			// 设置商家ID
			goods.setSellerId(sellerId);

			// 调用服务层查询数据
			return goodsService.findGoodsByPage(goods, page, rows);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/** 根据主键id查询商品 */
	@GetMapping("/findOne")
	public Goods findOne(Long id){
		try {
			return goodsService.findOne(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 修改商品 */
	@PostMapping("/update")
	public boolean update(@RequestBody Goods goods){
		try{
			// 修改商品(SPU、商品描述、SKU)
			goodsService.updateGoods(goods);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
}
