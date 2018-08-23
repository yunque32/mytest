package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.pojo.ContentCategory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {

    @Reference(timeout = 10000)
    private ContentCategoryService contentCategoryService;

    @GetMapping("/findByPage")
    public PageResult save(ContentCategory contentCategory,
                           @RequestParam(value = "page",defaultValue = "1")Integer page,
                           @RequestParam(value = "rows",defaultValue = "10")Integer rows){
        try {
            return contentCategoryService.findByPage(contentCategory,page,rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/findOne")
    public ContentCategory findOne(Long id){
        try {
            return contentCategoryService.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/save")
    public boolean save(@RequestBody  ContentCategory contentCategory){
        try {
            contentCategoryService.save(contentCategory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/update")
    public boolean update(@RequestBody ContentCategory contentCategory){
        try {
            contentCategoryService.update(contentCategory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            contentCategoryService.deleteAll(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/findAll")
    public List<ContentCategory> findAll(){
        try {
            return contentCategoryService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
