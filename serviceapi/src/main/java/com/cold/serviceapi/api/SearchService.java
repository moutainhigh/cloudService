package com.cold.serviceapi.api;

import com.cold.pojo.IndexDto;
import com.cold.pojo.PatentVo;
import com.cold.pojo.SearchRequest;
import com.cold.response.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/10/11 08:35
 * @Description:Feign抽象接口类
 */
@Component
@FeignClient("searchservice")
public interface SearchService {
        /**
         * 当使用Feign时，如果发送的是get请求，那么需要在请求参数前加上@RequestParam
         * feign中你可以有多个@RequestParam，但只能有不超过一个@RequestBody。
         * @param kw
         * @param count
         * @param searchType 检索类型 1:中英 2:英中
         * @return
         */
        @ResponseBody
        @RequestMapping(value="/api/basicSearch")
        List<PatentVo> basicSearch(@RequestParam String kw,@RequestParam Integer searchType,@RequestParam Integer count);

        @ResponseBody
        @RequestMapping(value="/api/search")
        List<PatentVo> search(@RequestBody SearchRequest searchRequest);
        /**
         * 分词
         * @param searchContent
         * @return
         */
        @ResponseBody
        @RequestMapping(value="/api/getIkAnalyzeSearchTerms")
        List<String> getSearchTerms(@RequestParam String searchContent);

        @ResponseBody
        @RequestMapping(value="/index/createIndex")
        CommonResult createIndex(@RequestBody List<IndexDto> indexDtos);

}
