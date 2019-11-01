package com.cold.searchservice.dao;

import com.cold.searchservice.entity.PatentZhEnIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: ohj
 * @Date: 2019/10/9 09:21
 * @Description:
 */
@Repository
public interface PatentZhEnRepository extends ElasticsearchRepository<PatentZhEnIndex,String> {
}
