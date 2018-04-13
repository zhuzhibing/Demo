package net.luculent.automatioin.laks.platform.dao;


import net.luculent.automatioin.laks.platform.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    UserEntity queryUserOne(@Param(value="loginName") String loginName, @Param(value="password")String password);

}