package com.cold.pojo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: ohj
 * @Date: 2019/10/18 08:14
 * @Description:
 */
@Data
public class SearchRequest implements Serializable {
    @NotNull
    private String kw;
    //检索方向
    @NotNull
    private String srcLan;
    private String tgtLan;
    private String catalog;
    private String applicants;//申请人/专利权人
    @Max(value = 500, message = "count不能大于500")
    @Min(value = 1, message = "count不能小于1")
    private Integer count;
}