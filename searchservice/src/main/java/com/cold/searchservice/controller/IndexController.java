package com.cold.searchservice.controller;

import com.cold.searchservice.common.api.CommonResult;
import com.cold.searchservice.dto.IndexDto;
import com.cold.searchservice.entity.PatentEntity;
import com.cold.searchservice.entity.PatentZhEnIndex;
import com.cold.searchservice.service.PatentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: ohj
 * @Date: 2019/9/23 15:53
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private PatentService patentService;

//    @RequestMapping("createIndex")
//    public String createIndex(){
//        PatentEntity patentEntity = new PatentEntity();
//        //patentEntity.setPatentNo("CN67450987");
//        patentEntity.setSource("8.权利要求1的组合物，其中所述碱性钙源以悬浮液或干粉形式使用，并且所述乳酸和柠檬酸的混合物以溶液形式使用。");
//        patentEntity.setTrans("8. A composition according to claim 1 wherein the alkaline calcium source is used in suspension or as dry powder and the mixture of lactic and citric acids is used as a solution.");
//        patentEntity.setMainClassification("H");
//        patentEntity.setLanguage("zh-en");
////        IndexQuery indexQuery = new IndexQueryBuilder().withObject(patentEntity).build();
////        elasticsearchTemplate.index(indexQuery);
//        patentService.save(patentEntity);
//        log.info("索引1创建成功");
//        return "success";
//    }
//
//    @RequestMapping("delAllIndex")
//    public String delAllIndex(){
//        patentService.deleteAll();
//        log.info("清除索引成功");
//        return "success";
//    }

    @RequestMapping("createIndex")
    public CommonResult createIndex(@RequestBody List<IndexDto> indexDtos){
        String prePno = "";
        PatentEntity patentEntity = null;
        List<PatentZhEnIndex> list = new ArrayList<>();
        int i = 0;
        for (IndexDto indexDto : indexDtos){
            String pno = indexDto.getPno();
            if(!pno.equalsIgnoreCase(prePno)){
                patentEntity = patentService.findOnePatentByNo(pno);
                prePno = pno;
            }
            if(patentEntity==null){
                i++;
                continue;
            }
            PatentZhEnIndex patentInfo = new PatentZhEnIndex();
            try {
                BeanUtils.copyProperties(patentInfo,patentEntity);
                patentInfo.setId(UUID.randomUUID().toString().replace("-",""));
                patentInfo.setSource(indexDto.getSource());
                patentInfo.setTrans(indexDto.getTrans());
                patentInfo.setZh2enaligned(indexDto.getS2talign());
                patentInfo.setEn2zhaligned(indexDto.getT2salign());
                list.add(patentInfo);
                if(i%100==0){
                    patentService.saveAllPatentZhEn(list);
                    log.info(i+":"+pno);
                    list.clear();
                }
                i++;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if(list.size()>0){
            patentService.saveAllPatentZhEn(list);
        }
        return CommonResult.success(null,"索引创建成功");
    }

    @RequestMapping("temporaryCreate")
    public void temporaryCreate(Integer startNum) throws IOException {
        log.info(startNum+":index");
        String basePath = "D:\\xcy\\ohj\\对齐20190927";
        long start = System.currentTimeMillis();
        String cnFile = "pre.clean.gl.zh";
        String enFile = "pre.clean.gl.en";
        String pnoFile = "pre.clean.gl.pNo";
        String zh2enalign = "zh2en.aligned";
        String en2zhalign = "en2zh.aligned";
        File file1 = new File(basePath,cnFile);
        File file2 = new File(basePath,enFile);
        File file3 = new File(basePath,pnoFile);
        File file4 = new File(basePath,zh2enalign);
        File file5 = new File(basePath,en2zhalign);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file1));
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);// 用10M的缓冲读取文本文件
        fis = new BufferedInputStream(new FileInputStream(file2));
        BufferedReader enReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);
        fis = new BufferedInputStream(new FileInputStream(file3));
        BufferedReader pnoReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);
        fis = new BufferedInputStream(new FileInputStream(file4));
        BufferedReader zhenReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);
        fis = new BufferedInputStream(new FileInputStream(file5));
        BufferedReader enzhReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);
        String line = "";
        int i = 0;

        String prePno = "";
        PatentEntity patentEntity = null;
        List<PatentZhEnIndex> list = new ArrayList<>();
        int startLine = 3144408;
        if(startNum!=null)startLine+=startNum;
        while ((line = reader.readLine()) != null) {
//            if(i==startLine+50){
//                break;
//            }

            String enLine = enReader.readLine();
            String pno = pnoReader.readLine();
            pno = pno.trim();
            String zhenalign = zhenReader.readLine();
            String enzhalign = enzhReader.readLine();
            if(i<startLine){
                i++;
                continue;
            }
            if(!pno.equalsIgnoreCase(prePno)){
                patentEntity = patentService.findOnePatentByNo(pno);
                prePno = pno;
            }
            if(patentEntity==null){
                log.info(">>>>>>>>>>>>>>>>>>>>>"+pno);
                i++;
                continue;
            }
            PatentZhEnIndex patentInfo = new PatentZhEnIndex();
            try {
                BeanUtils.copyProperties(patentInfo,patentEntity);
                patentInfo.setId(UUID.randomUUID().toString().replace("-",""));
                patentInfo.setSource(line);
                patentInfo.setTrans(enLine);
                //patentInfo.setLanguage("zh-en");
                patentInfo.setZh2enaligned(zhenalign);
                patentInfo.setEn2zhaligned(enzhalign);
                list.add(patentInfo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if(i%100==0){
                patentService.saveAllPatentZhEn(list);
                System.out.println(i+":"+pno);
                list.clear();
            }
            i++;
        }
        if(list.size()>0){
            patentService.saveAllPatentZhEn(list);
            list.clear();
        }
        fis.close();
        reader.close();
        enReader.close();
        pnoReader.close();
        zhenReader.close();
        enzhReader.close();
        long end = System.currentTimeMillis();
        log.info("索引创建完毕,共"+i+"条,用时:"+(end-start)/1000l+"秒");
    }


}