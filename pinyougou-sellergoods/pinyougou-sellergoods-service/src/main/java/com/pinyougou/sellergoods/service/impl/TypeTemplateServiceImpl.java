package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service(interfaceName="com.pinyougou.sellergoods.service.TypeTemplateService")
@Transactional(readOnly=false)
public class TypeTemplateServiceImpl implements TypeTemplateService {
	@Autowired
	private TypeTemplateMapper typeTemplateMapper;
	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;

	@Autowired
    private RedisTemplate redisTemplate;

	public PageResult findByPage(TypeTemplate typeTemplate,
								 Integer page, Integer rows){
		try{
			// 开始分页
			PageInfo<TypeTemplate> pageInfo = PageHelper.startPage(page, rows)
					.doSelectPageInfo(new ISelect() {
				@Override
				public void doSelect() {
					typeTemplateMapper.findAll(typeTemplate);
				}
			});
			return new PageResult(pageInfo.getTotal(), pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 添加类型模版 */
	public void saveTypeTemplate(TypeTemplate typeTemplate){
		try{
			typeTemplateMapper.insertSelective(typeTemplate);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改类型模版 */
	public void updateTypeTemplate(TypeTemplate typeTemplate){
		try{
			typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 删除类型模版 */
	public void deleteTypeTemplate(Long[] ids){
		try{
			// 创建Example delete tb_type_template where id in (?,?,?)
			Example example = new Example(TypeTemplate.class);
			// 创建条件对象
			Example.Criteria criteria = example.createCriteria();
			// 添加in 条件 where in (?,?)
			criteria.andIn("id", Arrays.asList(ids));
			typeTemplateMapper.deleteByExample(example);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询类型模版 */
	public TypeTemplate findOne(Long id){
		try {
			return typeTemplateMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** 根据类型模版id查询规格与规格选项 */
	public List<Map> findSpecByTemplateId(Long id){
		try {
			//  [{"id":27,"text":"网络", "options" : [{},{}]},
			//  {"id":32,"text":"机身内存","options":[{},{}]}]

			// 根据主键id查询类型模版
			TypeTemplate typeTemplate = findOne(id);
			// 获取规格json字符串，把它转化成 List<Map>
			// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
			List<Map> specList = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);
			// 迭代List集合
			for (Map map : specList){
				// 获取id的值 规格的主键id
				Long specId = Long.valueOf(map.get("id").toString());
				// 查询tb_specification_option规格选项
				// select * from tb_specification_option where spec_id = ?
				SpecificationOption so = new SpecificationOption();
				so.setSpecId(specId);
				List<SpecificationOption> soList = specificationOptionMapper.select(so);
				// map : {"id":27,"text":"网络"}
				// map: {"id":27,"text":"网络", "options" : [{},{}]}
				map.put("options", soList);
			}
			return specList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void saveToRedis(){
        try {
            List<TypeTemplate> typeTemplates = typeTemplateMapper.selectAll();
            for (TypeTemplate typeTemplate : typeTemplates) {
                List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
                redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(),brandList);

                redisTemplate.boundHashOps("specList").put(typeTemplate.getId(),
                        findSpecByTemplateId(typeTemplate.getId()));
            }
        } catch (Exception e) {
            System.out.println("把类型模版中的品牌与规格选项数据存入Redis 失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public TypeTemplate findTypeTemplateType(Long id) {
        try{
            return typeTemplateMapper.selectByPrimaryKey(id);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }

}