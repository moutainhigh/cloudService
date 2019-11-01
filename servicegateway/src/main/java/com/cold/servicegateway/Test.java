package com.cold.servicegateway;

import com.alibaba.fastjson.JSONObject;
import com.cold.pojo.SearchRequest;

/**
 * @Auther: ohj
 * @Date: 2019/10/23 15:16
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String kw = "hands or with";
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSrcLan("en");
        searchRequest.setTgtLan("zh");
        searchRequest.setKw(kw);
        searchRequest.setCount(100);
        String json = JSONObject.toJSONString(searchRequest);
        System.out.println(json);
    }
}