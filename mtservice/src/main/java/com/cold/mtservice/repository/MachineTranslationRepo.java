package com.cold.mtservice.repository;

import com.cold.mtservice.client.IMachineTranslationService;
import com.cold.mtservice.client.MachineTranslationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther: ohj
 * @Date: 2019/10/16 07:54
 * @Description: 机翻接口注入
 */
@Configuration
public class MachineTranslationRepo {
    @Value("${mtservice.url}")
    private String wsUrl;
    @Bean
    public IMachineTranslationService machineTranslationService(){
        IMachineTranslationService machineTranslationService = null;
        try {
            URL url = new URL(wsUrl);
            MachineTranslationService machineTranslationService1 = new MachineTranslationService(url);
            machineTranslationService = machineTranslationService1.getBasicHttpBindingIMachineTranslationService();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    return machineTranslationService;
    }
}