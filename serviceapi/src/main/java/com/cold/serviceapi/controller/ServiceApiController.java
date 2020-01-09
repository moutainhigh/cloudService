package com.cold.serviceapi.controller;

import com.cold.pojo.IndexDto;
import com.cold.pojo.PatentVo;
import com.cold.pojo.SearchRequest;
import com.cold.response.CommonResult;
import com.cold.serviceapi.api.SearchService;
import com.cold.serviceapi.service.SearchResultService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @Auther: ohj
 * @Date: 2019/10/11 15:24
 * @Description:
 * 接口
 */
@Slf4j
@RestController
@RequestMapping(value="/api")
public class ServiceApiController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private SearchResultService searchResultService;
    @RequestMapping(value="/basicSearch")
    public List<PatentVo> basicSearch(@NotNull(message = "参数错误,检索词")String kw, @NotNull(message = "参数错误,检索类型") @RequestParam(defaultValue = "1") Integer searchType, @RequestParam(defaultValue = "100") Integer count) {
        if(StringUtils.isBlank(kw)){
            return new ArrayList<>();
        }
        String terms = kw;
        if(searchType==1){//中到英,原文分词后匹配
            List<String> segList = searchService.getSearchTerms(kw);
            terms = StringUtils.join(segList," ");
        }
        log.info("检索词:{},检索类型:{},结果数:{}",terms,searchType,count);
        List<PatentVo> patentVos = searchService.basicSearch(terms,searchType, count);
        return patentVos;
    }

    @RequestMapping(value="/search")
    public CommonResult search(@Valid @RequestBody SearchRequest searchRequest){
        String kw = searchRequest.getKw();
        String terms = kw;
        boolean removeTransBlank = false;
        if(searchRequest.getSrcLan().equalsIgnoreCase("zh")){//中到英,原文分词后匹配
            List<String> segList = searchService.getSearchTerms(kw);
            terms = StringUtils.join(segList," ");
        }else{
            removeTransBlank = true;
        }
        log.info("检索词:{},检索类型:{},分类:{},结果数:{}",terms,searchRequest.getSrcLan()+"/"+searchRequest.getTgtLan(),searchRequest.getCatalog(),searchRequest.getCount());
        List<PatentVo> patentVos = searchService.search(searchRequest);
        Map<String,List<PatentVo>> resMap = searchResultService.getTermTransResult(terms,patentVos,removeTransBlank);
        Comparator<Map.Entry<String, List<PatentVo>>> comp = (o1, o2) -> o2.getValue().size() - o1.getValue().size();
        Map<String,List<PatentVo>> sortedMap = Maps.newLinkedHashMap();
        resMap.entrySet().stream().sorted(comp).forEachOrdered(entry -> {
            sortedMap.put(entry.getKey(), entry.getValue());
        });
//                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
//        List<Map.Entry<String,List<PatentVo>>> list = Lists.newArrayList(resMap.entrySet());
//        list.stream()
//                .sorted(Comparator.comparingInt(entry -> entry.getValue().size()))
//                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        //Comparator<Map.Entry<String, List<PatentVo>>> comparator = Comparator.comparingInt(List::size);

//        resMap.entrySet().stream().sorted((Comparator<? super Map.Entry<String, List<PatentVo>>>) Map.Entry.comparingByValue(comparator).reversed())
//                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return CommonResult.success(sortedMap);
    }

    @PostMapping("searchPatentInfo")
    public CommonResult searchPatentInfo(@RequestParam(value = "fileNo") String fileNo){
        log.info("查询专利信息:{}",fileNo);
        return searchService.searchPatentInfo(fileNo);
    }
    @RequestMapping(value="/createIndex")
    public CommonResult createIndex(@RequestBody List<IndexDto> indexDtos){
        log.info("创建索引:{}",indexDtos.size());
        return searchService.createIndex(indexDtos);
    }

    @RequestMapping(value="/exportSql2Index")
    public CommonResult exportSql2Index(@RequestBody List<PatentVo> patentVos){
        log.info("数据库数据导出,创建索引:{}",patentVos.size());
        return searchService.exportSql2Index(patentVos);
    }
}