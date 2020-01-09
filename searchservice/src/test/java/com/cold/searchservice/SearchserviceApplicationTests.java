package com.cold.searchservice;

import com.cold.searchservice.common.utils.FileUtils;
import com.cold.searchservice.common.utils.JsonUtils;
import com.cold.searchservice.dao.PatentMapper;
import com.cold.searchservice.entity.PatentEntity;
import com.cold.searchservice.entity.PatentZhEnIndex;
import com.cold.searchservice.entity.TmxPatentEntity;
import com.cold.searchservice.service.PatentService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchserviceApplicationTests {

    @Autowired
    private PatentService patentService;
    //@Test
    public void contextLoads() throws IOException {
//        List<PatentEntity> list = patentService.findPatentByNo("201680021861.5");
//        list.forEach(patentInfo -> {
//            System.out.println(patentInfo);
//        });
//        PatentEntity patentEntity = patentService.findPatentByNo("201680021861.5");
//        System.out.println(patentEntity);
        //patentService.deleteAll();
        String basePath = "D:\\语料\\中英专利提取对齐关系语料\\对齐20190927\\";
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
        startLine+=140293 ;
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
                System.out.println(">>>>>>>>>>>>>>>>>>>>>"+pno);
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

            if(i%1000==0){
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
        System.out.println("索引创建完毕,共"+i+"条,用时:"+(end-start)/1000l+"秒");
    }
    //@Test
//    public void testSavePatInfo(){
//        long start = System.currentTimeMillis();
//        String enter = "\r\n";
//        int bufSize = 1000000;//一次读取的字节长度
//        String pnoFile = "D:\\语料\\中英专利提取语料\\new-pNo-11434571.txt.utf8";
//        File fout = new File("E:\\new-patinfo.txt");//写出的文件
//        File file = new File(pnoFile);
//        try {
//            FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
//            ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);
//            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
//            BufferedReader pnoReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);
//            String pno = "";
//            int i = 0;
//            String prePno = "";
//            PatentEntity patentEntity = null;
//            while ((pno = pnoReader.readLine()) != null) {
//                if(!pno.equalsIgnoreCase(prePno)){
//                    patentEntity = patentService.findPatentByNo(pno);
//                    prePno = pno;
//                }
//
//                String json = JsonUtils.objectToJson(patentEntity);
//                json = json==null?enter:json+enter;
//                FileUtils.writeFileByLine(fcout,wBuffer,json);
//                //if(i==50)break;
//             if(i%10000==0){
//                System.out.println(i+":"+patentEntity);
//            }
//                i++;
//            }
//            if (fcout.isOpen()) {
//                fcout.close();
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("数据写入完毕,共"+i+"条,用时:"+(end-start)/1000l+"秒");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //@Test
//    public void createPatinfoIndex(){
//        List<PatentEntity> patentEntities = patentService.findAll();
//        System.out.println("数据库查询完毕,共"+patentEntities.size()+"条");
//        int i = 0;
//        List<PatentEntity> list = new ArrayList<>();
//        for (PatentEntity patentEntity : patentEntities){
//            patentEntity.setFileNo(patentEntity.getFileNo().trim());
//            patentEntity.setIpphPub(patentEntity.getIpphPub().trim());
//            patentEntity.setPublicationNumber(patentEntity.getPublicationNumber().trim());
//            patentEntity.setPubDate(patentEntity.getPubDate().trim());
//            patentEntity.setIpphApp(patentEntity.getIpphApp().trim());
//            patentEntity.setApplicationNumber(patentEntity.getApplicationNumber().trim());
//            patentEntity.setAppDate(patentEntity.getAppDate().trim());
//            patentEntity.setMainClassification(patentEntity.getMainClassification().trim());
//            patentEntity.setCountryOriginalApp(patentEntity.getCountryOriginalApp().trim());
//            patentEntity.setEntryNationalStageDate(patentEntity.getEntryNationalStageDate().trim());
//            list.add(patentEntity);
//            if(i%10000==0){
//                patentService.saveAllPatent(list);
//                list.clear();
//                System.out.println(i+":"+patentEntity);
//            }
//            i++;
//        }
//        if(list.size()>0){
//            patentService.saveAllPatent(list);
//        }
//    }

   // @Test
//    public void createTradosCorpus() throws IOException {
//        long start = System.currentTimeMillis();
//        String cnFile = "D:\\语料\\中英专利提取语料\\split\\split_cn.txt";
//        String enFile = "D:\\语料\\中英专利提取语料\\split\\split_en.txt";
//        String pnoFile = "D:\\语料\\中英专利提取语料\\split\\split_pno.txt";
//        File file1 = new File(cnFile);
//        File file2 = new File(enFile);
//        File file3 = new File(pnoFile);
//        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file1));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);// 用10M的缓冲读取文本文件
//        fis = new BufferedInputStream(new FileInputStream(file2));
//        BufferedReader enReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);
//        fis = new BufferedInputStream(new FileInputStream(file3));
//        BufferedReader pnoReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 10 * 1024 * 1024);
//        String line = "";
//        int i = 0;
//        List<TmxPatentEntity> list = new ArrayList<>();
//        while ((line = reader.readLine()) != null) {
//            String enLine = enReader.readLine();
//            String pno = pnoReader.readLine();
//            String uuid = UUID.randomUUID().toString().replace("-","");
//            TmxPatentEntity tmxPatentEntity = new TmxPatentEntity(uuid,line,enLine,pno);
//            list.add(tmxPatentEntity);
//            if(i%10000==0){
//                patentService.saveAll(list);
//                list.clear();
//                System.out.println(i+":"+tmxPatentEntity);
//            }
//            i++;
//        }
//        if(list.size()>0){
//            patentService.saveAll(list);
//        }
//        fis.close();
//        reader.close();
//        enReader.close();
//        pnoReader.close();
//        long end = System.currentTimeMillis();
//        System.out.println("索引创建完毕,共"+i+"条,用时:"+(end-start)/1000l+"秒");
//    }

    //@Test
    public void testSearch(){
        String kw = "作为可应用本发明的无线通信系统的示例";
        String pno = "201680046983.X";
        Map<String,String> queryNames = new HashMap();
        queryNames.put("source",kw);
        //queryNames.put("pno",pno);
        Page<TmxPatentEntity> page = patentService.pageQuery4tmx(0,10,queryNames);
        page.forEach(tmxPatentEntity -> {
            System.out.println(tmxPatentEntity);
        });
//        Iterator<TmxPatentEntity> tmxPatentEntities = page.iterator();
//        tmxPatentEntities.forEachRemaining(tmxPatentEntity -> {
//            System.out.println(tmxPatentEntity);
//        });
    }

    @Test
    public void testSearch2(){
//        String pno = "201680021746.8";
//        String fileno = "CN2018107529719A";
//        PatentEntity entity = patentService.findOnePatentByNo(fileno);
//        System.out.println("2222"+entity);
        Map<String,String> queryNames = new HashMap();
        queryNames.put("applicationNumber","201680045129.1");
        //queryNames.put("applicants","耐克");
//        Page<PatentEntity> page = patentService.pageQuery(0,10,queryNames);
//        page.forEach(patentEntity -> {
//            System.out.println(patentEntity);
//        });
        PatentEntity patentEntity = patentService.findOnePatentByNo("CN1995001101208A");
        System.out.println(patentEntity);
//        List<PatentZhEnIndex> list = patentService.pageQueryZhen(0,10,queryNames);
//        list.forEach(patentZhEnIndex -> {
//            System.out.println(patentZhEnIndex.getApplicants());
//        });
//        Iterator<TmxPatentEntity> tmxPatentEntities = page.iterator();
//        tmxPatentEntities.forEachRemaining(tmxPatentEntity -> {
//            System.out.println(tmxPatentEntity);
//        });
    }

}
