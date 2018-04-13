package net.luculent.automatioin.laks.platform.service;

import net.luculent.automatioin.laks.platform.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserEntity queryUser(String loginName,String password);

}
