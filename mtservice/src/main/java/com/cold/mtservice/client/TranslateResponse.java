
package com.cold.mtservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TranslateResult" type="{http://schemas.datacontract.org/2004/07/TranslationWcfService}MachineTranslationResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "translateResult"
})
@XmlRootElement(name = "TranslateResponse", namespace = "http://tempuri.org/")
public class TranslateResponse {

    @XmlElement(name = "TranslateResult", namespace = "http://tempuri.org/")
    protected MachineTranslationResult translateResult;

    /**
     * ��ȡtranslateResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link MachineTranslationResult }
     *     
     */
    public MachineTranslationResult getTranslateResult() {
        return translateResult;
    }

    /**
     * ����translateResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link MachineTranslationResult }
     *     
     */
    public void setTranslateResult(MachineTranslationResult value) {
        this.translateResult = value;
    }

}
