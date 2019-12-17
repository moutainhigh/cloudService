package com.cold.searchservice.service.impl;

import com.cold.searchservice.dao.PatentRepository;
import com.cold.searchservice.dao.PatentZhEnRepository;
import com.cold.searchservice.dao.TmxCorpusPatentRepository;
import com.cold.searchservice.entity.PatentEntity;
import com.cold.searchservice.entity.PatentZhEnIndex;
import com.cold.searchservice.entity.TmxPatentEntity;
import com.cold.searchservice.mapper.ESResultMapper;
import com.cold.searchservice.service.PatentService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: ohj
 * @Date: 2019/9/24 09:18
 * @Description:
 */
@Service
public class PatentServiceImpl implements PatentService {

    @Autowired
    private PatentRepository patentRepository;
    @Autowired
    private PatentZhEnRepository patentZhEnRepository;
    @Autowired
    private TmxCorpusPatentRepository tmxCorpusPatentRepository;
//    @Autowired
//    private PatentMapper patentMapper;
    @Resource
    private ESResultMapper esResultMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Override
    public long count() {
        return patentRepository.count();
    }

    @Override
    public void saveAll(List<TmxPatentEntity> entities) {
        tmxCorpusPatentRepository.saveAll(entities);
    }

    @Override
    public void saveAllPatent(List<PatentEntity> patentEntities) {
        patentRepository.saveAll(patentEntities);
    }

    @Override
    public void saveAllPatentZhEn(List<PatentZhEnIndex> patentEntities) {
        patentZhEnRepository.saveAll(patentEntities);
    }

    @Override
    public TmxPatentEntity save(TmxPatentEntity tmxPatentEntity) {
        return tmxCorpusPatentRepository.save(tmxPatentEntity);
    }

    @Override
    public PatentEntity save(PatentEntity patentEntity) {
        return patentRepository.save(patentEntity);
    }

    @Override
    public void delete(PatentEntity commodity) {
        patentRepository.delete(commodity);
//        commodityRepository.deleteById(commodity.getSkuId());
    }

    @Override
    public void deleteAll() {
        patentRepository.deleteAll();
    }

    @Override
    public Iterable<PatentEntity> getAll() {
        return patentRepository.findAll();
    }

    @Override
    public List<PatentEntity> getBySource(String source) {
        List<PatentEntity> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("source", source);
        Iterable<PatentEntity> iterable = patentRepository.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    @Override
    public Page<PatentEntity> pageQuery(Integer pageNo, Integer pageSize,Map<String,String> queryParams) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        //matchPhraseQuery不会分词
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable);
        queryParams.forEach((t,v)->{
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery(t, v));
        });
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchPhraseQuery("source", kw))
//                .withPageable(PageRequest.of(pageNo, pageSize))
//                .build();
        return patentRepository.search(nativeSearchQueryBuilder.build());
    }

//    @Override
//    public PatentEntity findPatentByNo(String patentNo) {
//        return patentMapper.findPatentByNo(patentNo);
//    }

    @Override
    public PatentEntity findOnePatentByNo(String patentNo) {
        Pageable pageable = PageRequest.of(0, 1);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("applicationNumber", patentNo))
                .withPageable(pageable)
                .build();
        Page<PatentEntity> patentEntityPage = patentRepository.search(searchQuery);
        List<PatentEntity> patentEntities =  patentEntityPage.getContent();
        if(!patentEntities.isEmpty()){
            return patentEntities.get(0);
        }
        return null;
    }

//    @Override
//    public List<PatentEntity> findAll() {
//        return patentMapper.findAll();
//    }

    @Override
    public Page<TmxPatentEntity> pageQuery4tmx(Integer pageNo, Integer pageSize,Map<String,String> queryParams) {
        // 分页参数
        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        // 分数，并自动按分排序
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name", q)),
//                        ScoreFunctionBuilders.weightFactorFunction(1000)) // 权重：name 1000分
//                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("message", q)),
//                        ScoreFunctionBuilders.weightFactorFunction(100)); // 权重：message 100分
//
//        // 分数、分页
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable)
//                .withQuery(functionScoreQueryBuilder).build();
        //matchQuery搜索的词会被分词器分词,分页pageconf0开始
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable);
        queryParams.forEach((t,v)->{
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery(t, v)).withHighlightFields(new HighlightBuilder.Field(t).preTags("<span style=\"color:red\">").postTags("</span>"));
        });

        //Page<TmxPatentEntity> searchPageResults = tmxCorpusPatentRepository.search(nativeSearchQueryBuilder.build());

        Page<TmxPatentEntity> searchPageResults = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(),TmxPatentEntity.class,esResultMapper);
        return searchPageResults;
    }

    @Override
    public List<PatentZhEnIndex> pageQueryZhen(Integer pageNo, Integer pageSize, Map<String, String> queryParams) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryParams.forEach((t,v)->{
            if("applicants".equals(t)){
                nativeSearchQueryBuilder.withQuery(queryBuilder.must(QueryBuilders.fuzzyQuery(t, v)));
                nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field(t).preTags("<em>").postTags("</em>"));
            }else{
                nativeSearchQueryBuilder.withQuery(queryBuilder.must(QueryBuilders.matchPhraseQuery(t, v).slop(0)));//完全匹配
            }

            //nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field(t));
        });
        Page<PatentZhEnIndex> searchPageResults = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(),PatentZhEnIndex.class,esResultMapper);
        //return patentZhEnRepository.search(nativeSearchQueryBuilder.build()).getContent();
        return searchPageResults.getContent();
    }
}