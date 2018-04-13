package net.luculent.automatioin.laks.platform.service.impl;

import net.luculent.automatioin.laks.platform.dao.UserDao;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity queryUser(String loginName, String password) {

        return userDao.queryUserOne(loginName,password);
    }

}
