
package com.cold.mtservice.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LanguageOption�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="LanguageOption">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="en"/>
 *     &lt;enumeration value="zh"/>
 *     &lt;enumeration value="jp"/>
 *     &lt;enumeration value="ko"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LanguageOption")
@XmlEnum
public enum LanguageOption {

    @XmlEnumValue("en")
    EN("en"),
    @XmlEnumValue("zh")
    ZH("zh"),
    @XmlEnumValue("jp")
    JP("jp"),
    @XmlEnumValue("ko")
    KO("ko");
    private final String value;

    LanguageOption(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LanguageOption fromValue(String v) {
        for (LanguageOption c: LanguageOption.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
