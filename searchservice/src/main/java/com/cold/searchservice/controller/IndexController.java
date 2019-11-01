package com.cold.searchservice.controller;

import com.cold.searchservice.entity.PatentEntity;
import com.cold.searchservice.service.PatentService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/9/23 15:53
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private PatentService patentService;

    @RequestMapping("createIndex")
    public String createIndex(){
        PatentEntity patentEntity = new PatentEntity();
        //patentEntity.setPatentNo("CN67450987");
        patentEntity.setSource("8.权利要求1的组合物，其中所述碱性钙源以悬浮液或干粉形式使用，并且所述乳酸和柠檬酸的混合物以溶液形式使用。");
        patentEntity.setTrans("8. A composition according to claim 1 wherein the alkaline calcium source is used in suspension or as dry powder and the mixture of lactic and citric acids is used as a solution.");
        patentEntity.setMainClassification("H");
        patentEntity.setLanguage("zh-en");
//        IndexQuery indexQuery = new IndexQueryBuilder().withObject(patentEntity).build();
//        elasticsearchTemplate.index(indexQuery);
        patentService.save(patentEntity);
        log.info("索引1创建成功");
        return "success";
    }

    @RequestMapping("delAllIndex")
    public String delAllIndex(){
        patentService.deleteAll();
        log.info("清除索引成功");
        return "success";
    }

}