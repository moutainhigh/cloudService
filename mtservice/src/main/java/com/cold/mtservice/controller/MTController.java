package com.cold.mtservice.controller;

import com.cold.mtservice.client.MachineTranslationService;
import com.cold.mtservice.dto.MTRequest;
import com.cold.mtservice.mt.MTTranslate;
import com.cold.mtservice.service.MTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: ohj
 * @Date: 2019/9/19 08:01
 * @Description:
 */
@RestController
@RequestMapping(value="/mt")
public class MTController {
    @Autowired
    private MTService mtService;
    @RequestMapping(value="/trans")
    public String trans(MTRequest mtRequest) {
        String trans = mtService.trans(mtRequest);
        return trans;
    }
}