package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * BrandServiceImpl
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2018-07-10<p>
 */
@Service(interfaceName = "com.pinyougou.sellergoods.service.BrandService")
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        // 开始分页
        // 第一个参数: 当前页码
        // 第二个参数：每页显示的记录数
        PageInfo<Brand> pageInfo = PageHelper.startPage(1, 25)
                .doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                brandMapper.selectAll();
            }
        });
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        for (Brand brand : pageInfo.getList()){
            System.out.println(brand.getName());
        }
        return pageInfo.getList();

    }

    /**
     * 分页查询品牌
     * @param brand 品牌对象
     * @param page 当前页码
     * @param rows 每页显示的记录数
     * @return 分页实体
     */
    public PageResult findByPage(Brand brand, Integer page, Integer rows){
        try{
            // 开始分页
            PageInfo<Brand> pageInfo = PageHelper.startPage(page, rows)
                    .doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                   brandMapper.findAll(brand);
                }
            });
            System.out.println("pageInfo.getList: " + pageInfo.getList());
            // pageInfo.getList() --> PageInfo对象 --> Page
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 添加品牌 */
    public void saveBrand(Brand brand){
        try{
            // 通用Mapper中的保存数据方法
           brandMapper.insertSelective(brand);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 修改品牌 */
    public void updateBrand(Brand brand){
        try{
            // 通用Mapper中的修改数据方法
            brandMapper.updateByPrimaryKeySelective(brand);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 批量删除 */
    public void deleteBrand(Long[] ids){
        try{
            // delete from 表 where id in(?,?,?)
            brandMapper.deleteAll(ids);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 查询所有的品牌(id与name) */
    public List<Map<String,Object>> findBrandByIdAndName(){
        try{
           return brandMapper.findBrandByIdAndName();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
