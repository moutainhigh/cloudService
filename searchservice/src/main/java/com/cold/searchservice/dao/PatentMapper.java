package com.cold.searchservice.dao;

import com.cold.searchservice.entity.PatentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: ohj
 * @Date: 2019/9/25 16:01
 * @Description:
 */
@Repository
@Mapper
public interface PatentMapper {
    @Select("select * from BIB_CNEN_YL3 where ApplicationNumber=#{patentNo}")
    @Results({  //@Result(property = "patentNo", column = "applicationNumber"),
            @Result(property = "countryOriginalApp", column = "ContryOriginalApp"),
            @Result(property = "ipphPub", column = "ipph_Pub"),
            @Result(property = "pubDate", column = "Pub_Date"),
            @Result(property = "ipphApp", column = "ipph_APP"),
            @Result(property = "appDate", column = "App_Date"),
            @Result(property = "cnTitle", column = "CN_Title"),
            @Result(property = "cnAbstract", column = "CN_Abstract")
    })
    PatentEntity findPatentByNo(String patentNo);
    @Select("select * from BIB_CNEN_YL3")
    @Results({  //@Result(property = "patentNo", column = "applicationNumber"),
            @Result(property = "countryOriginalApp", column = "ContryOriginalApp"),
            @Result(property = "ipphPub", column = "ipph_Pub"),
            @Result(property = "pubDate", column = "Pub_Date"),
            @Result(property = "ipphApp", column = "ipph_APP"),
            @Result(property = "appDate", column = "App_Date"),
            @Result(property = "cnTitle", column = "CN_Title"),
            @Result(property = "cnAbstract", column = "CN_Abstract")
    })
    List<PatentEntity> findAll();
}
