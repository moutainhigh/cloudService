package com.cold.serviceapi.domain;

import com.cold.pojo.PatentVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/10/14 08:27
 * @Description:术语结果
 */
@Data
public class TermResult implements Serializable {
    private String termTrans;
    private List<PatentVo> patentVos;
}