/**   
* @Title: SearchServiceImpl.java 
* @Package cn.itheima.jd.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 传智 小杨老师 
* @date 2018-7-4 下午12:25:31 
* @version V1.0   
*/
package cn.itheima.jd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itheima.jd.po.Product;
import cn.itheima.jd.po.Result;
import cn.itheima.jd.service.SearchService;

/** 
 * @ClassName: SearchServiceImpl 
 * @Description: 搜索service实现类 
 * @author 传智 小杨老师  
 * @date 2018-7-4 下午12:25:31 
 *  
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	// 注入HttpSolrServer
	@Autowired
	private HttpSolrServer httpSolrServer;

	/**
	 * 搜索商品
	 */
	public Result searchProduct(String queryString, String catalog_name,
			String price, Integer page, String sort) {
		
		// 1.创建查询对象（SolrQuery）
		SolrQuery sq = new SolrQuery();
		
		// 1.1.设置查询关键词
		/**
		 * 如果queryString为空，搜索全部；否则，搜索指定的关键词
		 */
		if(StringUtils.isNotBlank(queryString)){
			sq.setQuery(queryString);
		}else{
			sq.setQuery("*:*");
		}
		
		// 1.2.设置默认搜索域
		sq.set("df", "product_keywords");
		
		// 1.3.设置过滤条件
		// 商品分类名称
		if(StringUtils.isNotBlank(catalog_name)){
			catalog_name = "product_catalog_name:"+catalog_name;
		}
		
		// 商品价格0-9
		if(StringUtils.isNotBlank(price)){
			String[] arr = price.split("-");
			
			price = "product_price:["+arr[0]+" TO "+arr[1]+"]";
		}
		
		sq.setFilterQueries(catalog_name,price);
		
		// 1.4.设置分页
		// 默认搜索第一页
		if(page == null){
			page = 1;
		}
		
		int pageSize = 10;// 每一页显示10条
		
		sq.setStart((page-1)*pageSize);
		sq.setRows(pageSize);
		
		// 1.5.设置排序
		// 1表示升序；其它是降序
		if("1".equals(sort)){
			sq.setSort("product_price", ORDER.asc);
		}else{
			sq.setSort("product_price", ORDER.desc);
		}
		
		// 1.6.设置高亮显示
		// 开始高亮显示
		sq.setHighlight(true);
		// 添加高亮显示的域
		sq.addHighlightField("product_name");
		// 设置html高亮显示的开始和结尾
		sq.setHighlightSimplePre("<font color='red'>");
		sq.setHighlightSimplePost("</font>");
		
		
		// 2.执行搜索，返回查询响应结果对象（QueryResponse）
		QueryResponse queryResponse = null;
		try {
			 queryResponse = httpSolrServer.query(sq);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		// 3.从QueryResponse中，获取搜索结果数据
		// 3.1.取出搜索的结果集
		SolrDocumentList resultList = queryResponse.getResults();
		
		// 3.2.取出高亮数据
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

		// 4.处理结果集
		// 4.1.创建Result对象
		Result result = new Result();
		
		// 4.2.设置当前页数
		result.setCurPage(page);
		
		// 4.2.设置页数
		// 总记录数
		int totals = (int)resultList.getNumFound();
		/**
		 * 计算页数：
		 * 	总记录数%页面大小。如果余数为0：pageCount = totals/pageSize;
		 * 如果余数不为0：pageCount = (totals/pageSize)+1;
		 */
		int pageCount = 0;
		if(totals%pageSize == 0){
			pageCount = totals/pageSize;
		}else{
			pageCount = (totals/pageSize)+1;
		}
		
		result.setPageCount(pageCount);
		
		// 4.3.设置总记录数
		result.setRecordCount(totals);
		
		// 4.4.设置商品结果集合list
		List<Product> productList = new ArrayList<Product>();
		for(SolrDocument doc:resultList){
			// 获取商品Id
			String pid = doc.get("id").toString();
			
			// 获取商品名称
			String pname = "";
			List<String> list = highlighting.get(pid).get("product_name");
			if(list != null &&
					list.size()>0){
				pname = list.get(0);
			}else{
				pname = doc.get("product_name").toString();
			}
			
			// 获取商品价格
			String pprice = doc.get("product_price").toString();
			
			// 获取商品图片
			String ppicture = doc.get("product_picture").toString();
			
			// 创建商品对象
			Product pro = new Product();
			pro.setPid(pid);
			pro.setName(pname);
			pro.setPrice(pprice);
			pro.setPicture(ppicture);
			
			productList.add(pro);
		}
		
		
		result.setProductList(productList);
		
		return result;
	}

}
