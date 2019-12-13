package com.cold.auth2server.service.impl;

import com.cold.auth2server.dao.RoleDao;
import com.cold.auth2server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<String> getRolesByUserId(int id) {
        return roleDao.getRolesByUserId(id);
    }
}
