package com.pinyougou.mapper;


import com.pinyougou.pojo.TypeTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TypeTemplateMapper extends Mapper<TypeTemplate> {

    /** 多条件分页查询类型模版 */
    List<TypeTemplate> findAll(@Param("typeTemplate") TypeTemplate typeTemplate);

    @Select("select id,name from tb_type_template order by id asc")
    List<Map<String,Object>> findTypeTemplateList();
}