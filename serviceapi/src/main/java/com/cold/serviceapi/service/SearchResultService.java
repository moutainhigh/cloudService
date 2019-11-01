package com.cold.serviceapi.service;

import com.cold.pojo.PatentVo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: ohj
 * @Date: 2019/10/14 08:24
 * @Description:
 */
public interface SearchResultService {
    Map<String,List<PatentVo>> getTermTransResult(String kw,List<PatentVo> patentVos,boolean removeTransBlank);
}
