package com.cold.searchservice.service;

import com.cold.searchservice.entity.PatentEntity;
import com.cold.searchservice.entity.PatentZhEnIndex;
import com.cold.searchservice.entity.TmxPatentEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Auther: ohj
 * @Date: 2019/9/24 09:18
 * @Description:
 */
public interface PatentService {
    long count();

    //trados索引
    TmxPatentEntity save(TmxPatentEntity patentEntity);
    //查词索引
    PatentEntity save(PatentEntity patentEntity);

    void saveAll(List<TmxPatentEntity> entities);

    void saveAllPatent(List<PatentEntity> patentEntities);

    void saveAllPatentZhEn(List<PatentZhEnIndex> patentEntities);

    void delete(PatentEntity patentEntity);

    void deleteAll();

    Iterable<PatentEntity> getAll();

    List<PatentEntity> getBySource(String source);

    Page<PatentEntity> pageQuery(Integer pageNo, Integer pageSize,Map<String,String> queryParams);

    PatentEntity findPatentByNo(String patentNo);

    PatentEntity findOnePatentByNo(String patentNo);

    List<PatentEntity> findAll();

    Page<TmxPatentEntity> pageQuery4tmx(Integer pageNo, Integer pageSize, Map<String,String> queryParams);

    List<PatentZhEnIndex> pageQueryZhen(Integer pageNo, Integer pageSize,Map<String,String> queryParams);
}