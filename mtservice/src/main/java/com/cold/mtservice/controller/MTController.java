package com.cold.mtservice.controller;

import com.cold.mtservice.dto.MTRequest;
import com.cold.mtservice.service.MTService;
import com.cold.mtservice.util.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ohj
 * @Date: 2019/11/4 08:01
 * @Description:  机翻接口
 */
@Slf4j
@RestController
@RequestMapping(value="/mtapi")
public class MTController {
    @Autowired
    private MTService mtService;
    @RequestMapping(value = "translation",method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String,Object> translation(@RequestBody MTRequest mtRequest) {

        //log.info(Base64Utils.decode(mtRequest.getOriginalText()));
        Map<String,Object> map = new HashMap<>();
        String code = "200";
        //String msg = "";
        boolean success = true;
        if(StringUtils.isBlank(mtRequest.getOriginalText())){
            code = "300";
            success = false;
            map.put("error_msg", "参数错误");
        }else{
            mtRequest.setOriginalText(Base64Utils.decode(mtRequest.getOriginalText()));
            log.info("机翻调用:[{}]",mtRequest);
            String result = mtService.trans(mtRequest);
            map.put("trans", result);
            //log.info(Base64Utils.decode(msg));
        }
//        originalText = Base64Utils.decode(originalText);
        map.put("code",code);
        //map.put("msg",msg);
        map.put("success",success);
        return map;
    }
}