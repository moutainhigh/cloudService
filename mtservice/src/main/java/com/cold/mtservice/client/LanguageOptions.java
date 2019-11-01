
package com.cold.mtservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LanguageOptions complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="LanguageOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Source" type="{http://schemas.datacontract.org/2004/07/TranslationWcfService}LanguageOption" minOccurs="0"/>
 *         &lt;element name="Target" type="{http://schemas.datacontract.org/2004/07/TranslationWcfService}LanguageOption" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LanguageOptions", propOrder = {
    "source",
    "target"
})
public class LanguageOptions {

    @XmlElement(name = "Source")
    protected LanguageOption source;
    @XmlElement(name = "Target")
    protected LanguageOption target;

    /**
     * ��ȡsource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link LanguageOption }
     *     
     */
    public LanguageOption getSource() {
        return source;
    }

    /**
     * ����source���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageOption }
     *     
     */
    public void setSource(LanguageOption value) {
        this.source = value;
    }

    /**
     * ��ȡtarget���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link LanguageOption }
     *     
     */
    public LanguageOption getTarget() {
        return target;
    }

    /**
     * ����target���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageOption }
     *     
     */
    public void setTarget(LanguageOption value) {
        this.target = value;
    }

}
