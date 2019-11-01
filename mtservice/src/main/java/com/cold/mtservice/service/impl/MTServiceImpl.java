package com.cold.mtservice.service.impl;

import com.cold.mtservice.client.IMachineTranslationService;
import com.cold.mtservice.dto.MTRequest;
import com.cold.mtservice.mt.MTTranslate;
import com.cold.mtservice.service.MTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: ohj
 * @Date: 2019/10/18 14:53
 * @Description:
 */
@Service
public class MTServiceImpl implements MTService {
    @Autowired
    private IMachineTranslationService machineTranslationService;
    @Autowired
    private MTTranslate mtTranslate;
    @Override
    public String trans(MTRequest mtRequest) {
        return mtTranslate.translate(machineTranslationService,mtRequest);
    }
}