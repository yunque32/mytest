package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    /** 根据主键id查询类型模版 */
    @GetMapping("/findOne")
    public TypeTemplate findOne(Long id){
        return typeTemplateService.findOne(id);
    }


    /** 根据类型模版id查询规格与规格选项 */
    @GetMapping("/findSpecByTemplateId")
    public List<Map> findSpecByTemplateId(Long id){
        // 返回值形式 [{"id":27,"text":"网络", "options" : [{},{}]},
        //  {"id":32,"text":"机身内存","options":[{},{}]}]
        return typeTemplateService.findSpecByTemplateId(id);
    }
}
