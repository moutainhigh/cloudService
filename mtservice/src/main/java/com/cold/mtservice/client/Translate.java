
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
 *         &lt;element name="translationParameter" type="{http://schemas.datacontract.org/2004/07/TranslationWcfService}MachineTranslationParameter" minOccurs="0"/>
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
    "translationParameter"
})
@XmlRootElement(name = "Translate", namespace = "http://tempuri.org/")
public class Translate {

    @XmlElement(namespace = "http://tempuri.org/")
    protected MachineTranslationParameter translationParameter;

    /**
     * ��ȡtranslationParameter���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link MachineTranslationParameter }
     *     
     */
    public MachineTranslationParameter getTranslationParameter() {
        return translationParameter;
    }

    /**
     * ����translationParameter���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link MachineTranslationParameter }
     *     
     */
    public void setTranslationParameter(MachineTranslationParameter value) {
        this.translationParameter = value;
    }

}
