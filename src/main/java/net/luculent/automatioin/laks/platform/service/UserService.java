package net.luculent.automatioin.laks.platform.service;

import net.luculent.automatioin.laks.platform.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {
    int addUser(Map<String, Object> user);

    List<Map<String, Object>> queryUser(Map<String, Object> param);

    UserEntity queryUser(String userName,String password);

    int updateUserById(Map<String, Object> user);

    Map<String, Object> queryUserByIdForApp(int userId);
}
