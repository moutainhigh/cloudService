package com.cold.serviceapi.service.impl;

import com.cold.pojo.PatentVo;
import com.cold.serviceapi.domain.TermVo;
import com.cold.serviceapi.service.SearchResultService;
import com.cold.util.AlignUtil;
import com.cold.util.CorrespondUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: ohj
 * @Date: 2019/10/14 08:32
 * @Description:
 */
@Service
public class SearchResultServiceImpl implements SearchResultService {
    @Override
    public Map<String, List<PatentVo>> getTermTransResult(String kw,List<PatentVo> patentVos,boolean removeTransBlank) {
        Map<String,List<PatentVo>> resMap = Maps.newHashMap();
        for (PatentVo patentVo : patentVos){
            List<String> transList = AlignUtil.getAlignTrans(kw,patentVo,removeTransBlank);
            for (String segTrans : transList){
                if(resMap.containsKey(segTrans)){
                    List<PatentVo> list = resMap.get(segTrans);
                    if(!list.contains(patentVo)){
                        list.add(patentVo);
                    }
                }else {
                    List<PatentVo> list = Lists.newArrayList();
                    list.add(patentVo);
                    resMap.put(segTrans,list);
                }
            }

        }
        return resMap;
    }
}