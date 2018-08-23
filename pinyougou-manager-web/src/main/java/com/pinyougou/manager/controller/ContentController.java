package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.Content;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference(timeout = 10000)
    private ContentService contentService;

    @GetMapping("/findByPage")
    public PageResult save(Content content,
                           @RequestParam(value = "page",defaultValue = "1")Integer page,
                           @RequestParam(value = "rows",defaultValue = "10")Integer rows){
        try {
            return  contentService.findByPage(content,page,rows);
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("findByPage方法执行失败");
        }
        return null;
    }
}
