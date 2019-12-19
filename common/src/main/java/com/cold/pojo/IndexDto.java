package com.cold.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: ohj
 * @Date: 2019/11/26 16:22
 * @Description:
 */
@Data
public class IndexDto implements Serializable {
    private String source;
    private String trans;
    private String pno;
    private String s2talign;
    private String t2salign;
}