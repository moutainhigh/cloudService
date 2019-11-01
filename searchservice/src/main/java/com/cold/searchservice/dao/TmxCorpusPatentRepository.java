package com.cold.searchservice.dao;

import com.cold.searchservice.entity.PatentEntity;
import com.cold.searchservice.entity.TmxPatentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: ohj
 * @Date: 2019/9/24 14:47
 * @Description:
 */
@Repository
public interface TmxCorpusPatentRepository extends ElasticsearchRepository<TmxPatentEntity,String> {
}