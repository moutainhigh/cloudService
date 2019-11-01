package com.cold.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: ohj
 * @Date: 2019/10/8 08:51
 * @Description:
 */
@Data
public class PatentVo implements Serializable {
    private String id;
    private String source;
    private String noSegmentSource;
    private String trans;
    private String aligned;//对齐关系
    private String language;
    private String fileNo;
    private String ipphPub;
    private String publicationNumber;
    private String pubDate;
    private String ipphApp;
    private String applicationNumber;
    private String appDate;
    private String priority;
    private String mainClassification;
    private String classificationsIPCR;
    private String cnTitle;
    private String applicants;
    private String inventors;
    private String agents;
    private String countryOriginalApp;
    private String entryNationalStageDate;
    private String cnAbstract;
    private String hilightText;
}