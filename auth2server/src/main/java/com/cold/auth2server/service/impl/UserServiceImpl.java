package com.cold.auth2server.service.impl;


import com.cold.auth2server.dao.UserDao;
import com.cold.auth2server.model.UserEntity;
import com.cold.auth2server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

//    @Autowired
//    private PictureService pictureService;

    @Override
    public UserEntity getUserEntityById(Integer id) {
        return userDao.getUserEntityById(id);
    }

    @Override
    public UserEntity getUserEntityByLoginName(String id) {
        return userDao.getUserEntityByLoginName(id);
    }

    @Override
    public UserEntity getEntityByGithubId(String login) {
        return userDao.getEntityByGithubId(login);
    }

    @Override
    public void insertUser(UserEntity userEntity) {
        userDao.insertUser(userEntity);
    }

    @Override
    public void updateUserHeadImg(int userId, String headImg) {
        userDao.updateHeadImg(userId, headImg);
    }

    @Override
    public UserEntity getEntityByQqid(String openid) {
        return userDao.getEntityByQqid(openid);
    }

    @Override
    public void insertUserByQq(UserEntity userEntity) {
        userDao.insertUserByQq(userEntity);
    }
}
