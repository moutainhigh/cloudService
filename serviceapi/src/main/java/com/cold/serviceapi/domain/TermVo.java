package com.cold.serviceapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auther: ohj
 * @Date: 2019/10/15 08:17
 * @Description:
 */
@Data
@AllArgsConstructor
public class TermVo {
    private String term;
    private int start;
    private int end;
    private int segIndex;
}