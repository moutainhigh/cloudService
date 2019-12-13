package com.cold.mtservice.mt;

import com.cold.mtservice.client.*;
import com.cold.mtservice.dto.MTRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Auther: ohj
 * @Date: 2019/9/19 11:01
 * @Description:
 */
@Component
public class MTTranslate {
    public String translate(IMachineTranslationService machineTranslationService,MTRequest mtRequest){
        MachineTranslationParameter translationParameter = new MachineTranslationParameter();
        LanguageOptions languageOptions = new LanguageOptions();
        languageOptions.setSource(LanguageOption.fromValue(mtRequest.getFrom()));
        languageOptions.setTarget(LanguageOption.fromValue(mtRequest.getTo()));
        translationParameter.setLanguageOptions(languageOptions);
        translationParameter.setTranslationClass(TranslationClass.H);
        String trans = "";
        try {
            translationParameter.setOriginalText(Base64.getEncoder().encodeToString(mtRequest.getOriginalText().getBytes("UTF-8")));
            MachineTranslationResult machineTranslationResult =  machineTranslationService.translate(translationParameter);
            trans = machineTranslationResult.getOrderedResult();
            //if(StringUtils.isNotBlank(trans))trans = new String(Base64.getDecoder().decode(trans.getBytes()),"UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return trans;
    }
}