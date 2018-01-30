package net.luculent.automatioin.laks.platform.dao;


import net.luculent.automatioin.laks.platform.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    int addUser(Map<String, Object> user);

    /**
     * 查询用户信息
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryUser(Map<String, Object> param);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateUserById(Map<String, Object> user);

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    int deleteUser(int userId);

    /**
     * 物理删除用户信息
     *
     * @param userId
     * @return
     */
    int deleteUserForReal(int userId);

    /**
     * 手机app查询用户信息
     *
     * @param userId
     * @return
     */
    Map<String, Object> queryUserByIdForApp(int userId);


    UserEntity queryUserOne(@Param(value="userName") String userName, @Param(value="password")String password);

}