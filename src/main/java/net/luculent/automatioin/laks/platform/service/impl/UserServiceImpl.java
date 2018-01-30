package net.luculent.automatioin.laks.platform.service.impl;

import net.luculent.automatioin.laks.platform.dao.UserDao;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public int addUser(Map<String, Object> user) {
//        BaseDataManageUtil.dataManage(user, true);
        return this.userDao.addUser(user);
    }

    public List<Map<String, Object>> queryUser(Map<String, Object> param) {
        return this.userDao.queryUser(param);
    }

    @Override
    public UserEntity queryUser(String userName, String password) {

        return userDao.queryUserOne(userName,password);
    }

    public int updateUserById(Map<String, Object> user) {
//        BaseDataManageUtil.dataManage(user, false);
        return this.userDao.updateUserById(user);
    }

    public Map<String, Object> queryUserByIdForApp(int userId){
        return this.userDao.queryUserByIdForApp(userId);
    }
}
