package com.cold.searchservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @Auther: ohj
 * @Date: 2019/9/29 09:20
 * @Description: @Field作用于属性上，经测试该注解的属性有时会与现有的属性冲突，造成异常，错误信息如下，所以建议es中映射已建立的情况下，不要使用该注解。
 */
@Document(indexName = "patent_zhen_index",shards = 3, replicas = 1)
@Data
public class PatentZhEnIndex implements Serializable {
    @Id
    private String id;
    //analyzer：分词器名称，ik_max_word即使用ik分词器的最细粒度的拆分 ik_smart会做最粗粒度的拆分
    //@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String source;
    //    @Field(type = FieldType.Keyword)
//    private String segmentSource;//经过分词的原文
    //@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String trans;
    //@Field(type = FieldType.Keyword)
    private String en2zhaligned;
    //@Field(type = FieldType.Keyword)
    private String zh2enaligned;

    //@Field(type = FieldType.Text)
    private String fileNo;
    //@Field(type = FieldType.Keyword)
    private String ipphPub;
    //@Field(type = FieldType.Keyword)
    private String publicationNumber;
    //@Field(type = FieldType.Keyword)
    private String pubDate;
    //@Field(type = FieldType.Keyword)
    private String ipphApp;
    //@Field(type = FieldType.Text)
    private String applicationNumber;
    //@Field(type = FieldType.Keyword)
    private String appDate;
    //@Field(type = FieldType.Keyword)
    private String priority;
    //@Field(type = FieldType.Keyword)
    private String mainClassification;
    //@Field(type = FieldType.Keyword)
    private String classificationsIPCR;
    //@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String cnTitle;
    //@Field(type = FieldType.Keyword)
    private String applicants;
    //@Field(type = FieldType.Keyword)
    private String inventors;
    //@Field(type = FieldType.Keyword)
    private String agents;
    //@Field(type = FieldType.Keyword)
    private String countryOriginalApp;
    //@Field(type = FieldType.Keyword)
    private String entryNationalStageDate;
    //@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String cnAbstract;
//    @Field(type = FieldType.Keyword,ignoreFields = {})
//    private String hilightText;
}