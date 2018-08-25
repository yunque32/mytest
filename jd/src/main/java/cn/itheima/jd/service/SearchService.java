/**   
* @Title: SearchService.java 
* @Package cn.itheima.jd.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 传智 小杨老师 
* @date 2018-7-4 下午12:22:34 
* @version V1.0   
*/
package cn.itheima.jd.service;

import cn.itheima.jd.po.Result;

/** 
 * @ClassName: SearchService 
 * @Description: 搜索service接口 
 * @author 传智 小杨老师  
 * @date 2018-7-4 下午12:22:34 
 *  
 */
public interface SearchService {
	
	/**
	 * 搜索商品
	 * 参数确定：根据提交的表单元素来确定
	 */
	Result searchProduct(String queryString, String catalog_name,
                         String price, Integer page, String sort);

}
