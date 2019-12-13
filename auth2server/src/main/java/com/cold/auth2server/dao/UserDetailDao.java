package com.cold.auth2server.dao;

import com.cold.auth2server.model.UserDetailEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailDao {

    void insert(UserDetailEntity userDetailEntity);

}
