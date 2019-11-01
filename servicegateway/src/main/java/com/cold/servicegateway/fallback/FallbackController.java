package com.cold.servicegateway.fallback;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ohj
 * @Date: 2019/10/21 16:05
 * @Description:
 */
@RestController
public class FallbackController {
    @RequestMapping("fallback")
    public Map<String, Object> fallback(){
        Map<String,Object> returnObject = new HashMap<>();
        returnObject.put("code", 500);
        returnObject.put("msg", "服务不可用，请稍后再试。");
        returnObject.put("time", System.currentTimeMillis());
        return returnObject;
    }

//    public void error(Httpserv){
//
//    }

}