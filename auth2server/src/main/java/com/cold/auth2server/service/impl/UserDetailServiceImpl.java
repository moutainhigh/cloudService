package com.cold.auth2server.service.impl;


import com.cold.auth2server.dao.UserDetailDao;
import com.cold.auth2server.model.UserDetailEntity;
import com.cold.auth2server.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailDao userDetailDao;

    @Override
    public void insert(UserDetailEntity userDetailEntity) {
        userDetailDao.insert(userDetailEntity);
    }
}
