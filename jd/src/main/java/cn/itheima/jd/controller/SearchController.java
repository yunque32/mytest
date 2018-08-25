/**   
* @Title: SearchController.java 
* @Package cn.itheima.jd.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 传智 小杨老师 
* @date 2018-7-4 下午12:08:49 
* @version V1.0   
*/
package cn.itheima.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itheima.jd.po.Result;
import cn.itheima.jd.service.SearchService;

/** 
 * @ClassName: SearchController 
 * @Description:搜索controller
 */
@Controller
public class SearchController {
	
	// 注入搜索service
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/list.action")
	public String list(Model model,String queryString, String catalog_name,
			String price, Integer page, String sort){
		
		// 1.搜索商品
		Result result = this.searchService
				.searchProduct(queryString, catalog_name, price, page, sort);
		
		// 2.响应搜索结果数据
		model.addAttribute("result", result);
		
		// 3.参数数据回显
		model.addAttribute("queryString", queryString);
		model.addAttribute("catalog_name",catalog_name );
		model.addAttribute("price", price);
		model.addAttribute("sort",sort );
		
		return "product_list";
	}
	
	

}
