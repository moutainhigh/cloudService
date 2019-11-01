package com.cold.searchservice.service;

import com.cold.searchservice.entity.TmxPatentEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/9/29 09:28
 * @Description:
 */
public class IndexerService {
    private static final String PAT_INDEX_NAME = "corpus_zhen";

    private static final String PAT_INDEX_TYPE = "corpus_zhen";
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public long bulkIndex(List<TmxPatentEntity> patentEntities) throws Exception {
        int counter = 0;
        try {
            //判断索引是否存在
            if (!elasticsearchTemplate.indexExists(PAT_INDEX_NAME)) {
                elasticsearchTemplate.createIndex(PAT_INDEX_NAME);
            }
            Gson gson = new Gson();
            List queries = new ArrayList();
            for (TmxPatentEntity patentEntity : patentEntities) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(patentEntity.getId());
                indexQuery.setSource(gson.toJson(patentEntity));
                indexQuery.setIndexName(PAT_INDEX_NAME);
                indexQuery.setType(PAT_INDEX_TYPE);
                queries.add(indexQuery);
                //分批提交索引
                if (counter % 1000 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    System.out.println("bulkIndex counter : " + counter);
                }
                counter++;
            }

            //不足批的索引最后不要忘记提交
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
            elasticsearchTemplate.refresh(PAT_INDEX_NAME);
            System.out.println("bulkIndex completed.");
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;

        }
        return -1;

    }
}