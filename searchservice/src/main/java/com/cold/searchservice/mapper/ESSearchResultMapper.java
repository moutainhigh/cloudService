package com.cold.searchservice.mapper;

import com.cold.searchservice.entity.PatentEntity;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/9/30 14:26
 * @Description:
 */
public class ESSearchResultMapper implements SearchResultMapper {
    private String field;
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
        List<PatentEntity> patentEntities = new ArrayList<>();
        SearchHits hits = response.getHits();
        for (SearchHit searchHit : hits) {
            if (hits.getHits().length <= 0) {
                return null;
            }
            PatentEntity patentEntity = new PatentEntity();
            String highLightMessage = searchHit.getHighlightFields().get(field).fragments()[0].toString();
//            poem.setId(Integer.parseInt(searchHit.getId()));
//            poem.setTitle(String.valueOf(searchHit.getSource().get("title")));
//            poem.setContent(String.valueOf(searchHit.getSource().get("content")));
            // 反射调用set方法将高亮内容设置进去
            try {
                String setMethodName = parSetName(field);
                Class<? extends PatentEntity> patClazz = patentEntity.getClass();
                Method setMethod = patClazz.getMethod(setMethodName, String.class);
                setMethod.invoke(patentEntity, highLightMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            patentEntities.add(patentEntity);
        }
        if (patentEntities.size() > 0) {
            return (AggregatedPage<T>) new PageImpl<T>((List<T>) patentEntities);
        }
        return null;
    }

    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

}