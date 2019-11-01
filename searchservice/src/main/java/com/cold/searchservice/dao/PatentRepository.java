package com.cold.searchservice.dao;

import com.cold.searchservice.entity.PatentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: ohj
 * @Date: 2019/9/29 10:03
 * @Description:
 */
@Repository
public interface PatentRepository extends ElasticsearchRepository<PatentEntity,String> {
}
