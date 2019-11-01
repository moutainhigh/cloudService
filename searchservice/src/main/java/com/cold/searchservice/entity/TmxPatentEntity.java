package com.cold.searchservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


/**
 * @Auther: ohj
 * @Date: 2019/9/27 09:34
 * @Description: trados检索语料
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "corpus_zhen",type = "corpus_zhen", shards = 3, replicas = 1)
public class TmxPatentEntity implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String source;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String trans;
    @Field(type = FieldType.Keyword)
    private String pno;
}