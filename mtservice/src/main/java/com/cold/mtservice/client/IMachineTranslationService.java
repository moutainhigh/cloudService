package com.cold.mtservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2018-01-04T12:56:14.669+08:00
 * Generated source version: 2.7.11
 * 
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "IMachineTranslationService")
@XmlSeeAlso({ObjectFactory.class})
public interface IMachineTranslationService {

    @WebMethod(operationName = "Translate", action = "http://tempuri.org/IMachineTranslationService/Translate")
    @Action(input = "http://tempuri.org/IMachineTranslationService/Translate", output = "http://tempuri.org/IMachineTranslationService/TranslateResponse")
    @RequestWrapper(localName = "Translate", targetNamespace = "http://tempuri.org/", className = "com.cold.client.Translate")
    @ResponseWrapper(localName = "TranslateResponse", targetNamespace = "http://tempuri.org/", className = "com.cold.client.TranslateResponse")
    @WebResult(name = "TranslateResult", targetNamespace = "http://tempuri.org/")
    public MachineTranslationResult translate(
            @WebParam(name = "translationParameter", targetNamespace = "http://tempuri.org/")
                    com.cold.mtservice.client.MachineTranslationParameter translationParameter
    );
}
