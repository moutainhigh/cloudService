package com.cold.searchservice.controller;

import com.cold.pojo.PatentVo;
import com.cold.pojo.SearchRequest;
import com.cold.response.CommonResult;
import com.cold.searchservice.entity.PatentEntity;
import com.cold.searchservice.entity.PatentZhEnIndex;
import com.cold.searchservice.service.PatentService;
import com.cold.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.SearchResult;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: ohj
 * @Date: 2019/10/10 15:47
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping(value="/api")
public class SearchController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private PatentService patentService;
    @PostMapping("basicSearch")
    public List<PatentVo> basicSearch(String kw,Integer searchType,@RequestParam(defaultValue = "100") Integer count){
        Map<String,String> map = new HashMap<>();
        switch (searchType){
            case 1://中到英
                map.put("source",kw);
                break;
            case 2://英到中
                map.put("trans",kw);
                break;
        }
        if(count>500)count = 500;
        List<PatentZhEnIndex> list = patentService.pageQueryZhen(0,count,map);
        List<PatentVo> patentVos = list.stream().map(patentZhEnIndex -> {
            PatentVo patentVo = new PatentVo();
            BeanUtils.copyProperties(patentZhEnIndex,patentVo);
            if(searchType==1){//中到英
                String noSegmentSource = StringUtil.removeBlank(patentVo.getSource());
                patentVo.setNoSegmentSource(noSegmentSource);
                patentVo.setAligned(patentZhEnIndex.getZh2enaligned());
            }else{
                String source = patentVo.getTrans();
                String trans = patentVo.getSource();
                String aligned = patentZhEnIndex.getEn2zhaligned();
                patentVo.setSource(source);
                patentVo.setTrans(trans);
                patentVo.setAligned(aligned);
            }
            String hilightText = patentVo.getSource().replace(kw,"<em>"+kw+"</em>");
            patentVo.setHilightText(hilightText);
            return patentVo;
        }).collect(Collectors.toList());
        return patentVos;
    }

    @PostMapping("search")
    public List<PatentVo> search(@Valid @RequestBody SearchRequest searchRequest){
        Map<String,String> map = new HashMap<>();
        switch (searchRequest.getSrcLan()){
            case "zh"://中到英
                map.put("source",searchRequest.getKw());
                break;
            case "en"://英到中
                map.put("trans",searchRequest.getKw());
                break;
        }
        if(StringUtils.isNotBlank(searchRequest.getCatalog())){
            map.put("mainClassification",searchRequest.getCatalog());
        }
        if(StringUtils.isNotBlank(searchRequest.getApplicants())){
            map.put("applicants",searchRequest.getApplicants());
        }
        int count = searchRequest.getCount();
        if(count>200)count = 200;
        log.info("检索词:{},检索类型:{},分类:{},结果数:{}",searchRequest.getKw(),searchRequest.getSrcLan()+"/"+searchRequest.getTgtLan(),searchRequest.getCatalog(),searchRequest.getCount());
        List<PatentZhEnIndex> list = patentService.pageQueryZhen(0,count,map);
        List<PatentVo> patentVos = list.stream().map(patentZhEnIndex -> {
            PatentVo patentVo = new PatentVo();
            BeanUtils.copyProperties(patentZhEnIndex,patentVo);
            if(searchRequest.getSrcLan().equalsIgnoreCase("zh")){//中到英
                String noSegmentSource = StringUtil.removeBlank(patentVo.getSource());
                patentVo.setNoSegmentSource(patentVo.getTrans());
                patentVo.setAligned(patentZhEnIndex.getZh2enaligned());
                String hilightText = noSegmentSource.replace(searchRequest.getKw(),"<em>"+searchRequest.getKw()+"</em>");
                patentVo.setHilightText(hilightText);
            }else{
                String source = patentVo.getTrans();
                String trans = patentVo.getSource();
                String aligned = patentZhEnIndex.getEn2zhaligned();
                patentVo.setSource(source);
                patentVo.setTrans(trans);
                patentVo.setAligned(aligned);
                String noSegmentSource = StringUtil.removeBlank(trans);
                patentVo.setNoSegmentSource(noSegmentSource);
                String hilightText = source.replace(searchRequest.getKw(),"<em>"+searchRequest.getKw()+"</em>");
                patentVo.setHilightText(hilightText);
            }

            return patentVo;
        }).collect(Collectors.toList());
        return patentVos;
    }
    @RequestMapping("getIkAnalyzeSearchTerms")
    public List<String> getIkAnalyzeSearchTerms(String searchContent) {
        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(elasticsearchTemplate.getClient(),
                AnalyzeAction.INSTANCE, "patent_zhen_index", searchContent);
        //ikRequest.setTokenizer("ik_max_word");
        ikRequest.setTokenizer("ik_smart");
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
        List<String> searchTermList = new ArrayList<>();
        ikTokenList.forEach(ikToken -> {
            searchTermList.add(ikToken.getTerm());
        });
        return searchTermList;
    }

    @PostMapping("searchPatentInfo")
    public CommonResult searchPatentInfo(@RequestParam(value = "fileNo") String fileNo){
        PatentEntity patentEntity = patentService.findOnePatentByNo(fileNo);
        if(patentEntity!=null){
            PatentVo patentVo = new PatentVo();
            BeanUtils.copyProperties(patentEntity,patentVo);
            return CommonResult.success(patentVo);
        }else{
            return CommonResult.success(null);
        }
    }
}