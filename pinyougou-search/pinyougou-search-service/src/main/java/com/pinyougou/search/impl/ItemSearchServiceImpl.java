package com.pinyougou.search.impl;

import com.pinyougou.search.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSearchServiceImpl implements ItemSearchService{

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveOrUpdate(List<SolrItem> solrItemList) {
        UpdateResponse updateResponse = solrTemplate.saveBeans(solrItemList);
        if (updateResponse.getStatus()==0){
            solrTemplate.commit();
        }else {
            solrTemplate.rollback();
        }
    }

    @Override
    public void delete(List<Long> ids) {
        Query query = new SimpleQuery();
        Criteria criteria = new Criteria("goodsId").in(ids);
        query.addCriteria(criteria);
        UpdateResponse updateResponse = solrTemplate.delete(query);
        if (updateResponse.getStatus()==0){
            solrTemplate.commit();
        }else {
            solrTemplate.rollback();
        }
    }

    @Override
    public Map<String, Object> search(Map<String, Object> params) {
        Map<String,Object> data=new HashMap<>();
        String keywords = (String) params.get("keywords");
        Integer page = (Integer) params.get("page");
        if (page==null||page<1){
            page=1;
        }
        Integer rows = (Integer) params.get("rows");
        if (rows==null){
            rows=20;
        }
        if (StringUtils.isNoneBlank(keywords)){
            keywords = keywords.replace(" ", "");
            HighlightQuery highlightQuery = new SimpleHighlightQuery();
            Criteria criteria = new Criteria("keywords").is(keywords);
            highlightQuery.addCriteria(criteria);


            HighlightOptions highlightOptions = new HighlightOptions();
            highlightOptions.addField("title");
            highlightOptions.setSimplePrefix("<font color='red'");
            highlightOptions.setSimplePostfix("</font>");
            highlightQuery.setHighlightOptions(highlightOptions);
            String categoryName = (String) params.get("category");
            if (StringUtils.isNoneBlank(categoryName)){
                Criteria criteria1 = new Criteria("category").is(categoryName);
                highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
            }
            String brandName = (String) params.get("brand");
            if (StringUtils.isNoneBlank(brandName)){
                Criteria criteria1 = new Criteria("brand").is(brandName);
                highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
            }
            Map<String,String> specMap = (Map<String, String>) params.get("spec");
            if (specMap !=null&& specMap.size()>0) {
                for (String key : specMap.keySet()) {
                    Criteria criteria1 = new Criteria("spec_" + key).is(specMap.get(key));
                    highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
            }
            String price = (String) params.get("price");
            if (StringUtils.isNoneBlank(price)){
                String[] priceArr = price.split("-");
                if (!"0".equals(priceArr[0])){
                    Criteria criteria1 = new Criteria("price").greaterThanEqual(priceArr[0]);
                    highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
                if (!"*".equals(priceArr[1])){
                    Criteria criteria1 = new Criteria("price").lessThanEqual(priceArr[1]);
                    highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
            }
            String sortField = (String) params.get("sortField");
            String sortValue = (String) params.get("sort");
            if (StringUtils.isNoneBlank(sortField)&&StringUtils.isNoneBlank(sortValue)){
                Sort sort = new Sort("ASC".equalsIgnoreCase(sortValue) ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                highlightQuery.addSort(sort);
            }
            highlightQuery.setOffset((page-1)*rows);
            highlightQuery.setRows(rows);
            HighlightPage<SolrItem> highlightPage = solrTemplate.queryForHighlightPage(highlightQuery, SolrItem.class);
            for(HighlightEntry<SolrItem> hp:highlightPage.getHighlighted()){
                SolrItem solrItem = hp.getEntity();
                if (hp.getHighlights().size()>0){
                    String title = hp.getHighlights().get(0).getSnipplets().get(0);
                    System.out.println("高亮内容"+title);
                    solrItem.setTitle(title);
                }
            }
            List<String> categoryList = searchCategoryList(keywords);

            if (StringUtils.isNoneBlank(categoryName)){
                Map<String, Object> brandSpecMap = searchBrandAndSpecList(categoryName);
                data.putAll(brandSpecMap);
            }else {
                if(categoryList.size()>0){
                    categoryName= categoryList.get(0);
                    Map<String,Object> brandSpecMap=searchBrandAndSpecList(categoryName);
                    data.putAll(brandSpecMap);
                }
            }
            data.put("categoryList",categoryList);
            data.put("rows",highlightPage.getContent());
            data.put("totalPages",highlightPage.getTotalPages());
            data.put("total",highlightPage.getTotalElements());
        }else {
            SimpleQuery query = new SimpleQuery("*:*");
            query.setOffset((page-1)*rows);
            query.setRows(rows);
            ScoredPage<SolrItem> scoredPage = solrTemplate.queryForPage(query, SolrItem.class);
            data.put("rows",scoredPage.getContent());
            data.put("totalPages",scoredPage.getTotalPages());
            data.put("total",scoredPage.getTotalElements());
        }
        return data;
    }
    private Map<String,Object> searchBrandAndSpecList(String categoryName){
        Map<String, Object> brandSpecMap = new HashMap<>();
        Object typeId = redisTemplate.boundHashOps("itemCats").get(categoryName);
        List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("brandList").get(typeId);
        brandSpecMap.put("brandList",brandList);
        List<Map> specList = (List<Map>) redisTemplate.boundHashOps("specList").get(typeId);
        brandSpecMap.put("specList",specList);
        return brandSpecMap;
    }

    private List<String> searchCategoryList(String keywords){
        List<String> categoryList = new ArrayList<>();
        SimpleQuery query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("keywords").is(keywords);
        query.addCriteria(criteria);
        GroupOptions groupOptions = new GroupOptions().addGroupByField("category");
        query.setGroupOptions(groupOptions);
        GroupPage<SolrItem> groupPage = solrTemplate.queryForGroupPage(query, SolrItem.class);
        GroupResult<SolrItem> groupResult = groupPage.getGroupResult("category");
        List<GroupEntry<SolrItem>> groupEntryList = groupResult.getGroupEntries().getContent();
        for (GroupEntry<SolrItem> groupEntry : groupEntryList) {
            System.out.println("商品分类:"+groupEntry.getGroupValue());
            categoryList.add(groupEntry.getGroupValue());
        }
        return categoryList;
    }

}
