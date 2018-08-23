package com.pinyougou.mapper;

import com.pinyougou.pojo.Goods;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface GoodsMapper extends Mapper<Goods>{
    List<Map<String,Object>> findGoodsByWhere(@Param("goods")Goods goods);

    void findAllByWhere(@Param("goods") Goods goods);

    void updateDeleteStatus(@Param("ids") Long[] ids,@Param("status")String status);


    void updateAuditStatus(@Param("ids") Long[] ids,@Param("isDelete") String status);

}