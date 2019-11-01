package com.cold.mtservice;

import com.cold.mtservice.client.*;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

/**
 * @Auther: ohj
 * @Date: 2019/9/19 09:18
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String url = "http://192.168.3.113:8082/Transformer/services/MachineTranslationService?wsdl";//测试地址
        try {
            MachineTranslationService machineTranslationService = new MachineTranslationService(new URL(url));//webservice客户端
            IMachineTranslationService iMachineTranslationService = machineTranslationService.getBasicHttpBindingIMachineTranslationService();
            String originalText = "机器翻译";//需要翻译的原文
            String srcLan = "zh";//语种
            String tgtLan = "en";
            MachineTranslationParameter translationParameter = new MachineTranslationParameter();
            LanguageOptions languageOptions = new LanguageOptions();
            languageOptions.setSource(LanguageOption.fromValue(srcLan));
            languageOptions.setTarget(LanguageOption.fromValue(tgtLan));
            translationParameter.setLanguageOptions(languageOptions);
            translationParameter.setTranslationClass(TranslationClass.H);
            String trans = "";
            try {
                translationParameter.setOriginalText(Base64.getEncoder().encodeToString(originalText.getBytes("UTF-8")));
                MachineTranslationResult machineTranslationResult =  iMachineTranslationService.translate(translationParameter);
                trans = machineTranslationResult.getOrderedResult();
                if(StringUtils.isNotBlank(trans))trans = new String(Base64.getDecoder().decode(trans.getBytes()),"UTF-8");
                System.out.println(trans);
            } catch (UnsupportedEncodingException e) {

            }
        } catch (MalformedURLException e) {

        }
    }
}