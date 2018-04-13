package net.luculent.automatioin.laks.platform.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 14:42 2018/2/28
 * @Modified By:
 */
@Mapper
public interface RoleAccessDao {

    /**
     * 用户角色分配
     *
     * @param userId
     * @param roleId
     */
    void allotUserRole(Integer userId, Integer roleId);

    /**
     * 用户角色删除
     *
     * @param userId
     * @param roleId
     */
    void deleteUserRole(Integer userId, Integer roleId);


    /**
     * 角色权限分配
     *
     * @param roleId
     * @param powerId
     */
    void allotRolePower(Integer roleId, Integer powerId);

    /**
     * 角色权限删除
     *
     * @param roleId
     * @param powerId
     */
    void deleteRolePower(Integer roleId, Integer powerId);


    /**
     * 菜单权限分配
     *
     * @param powerId
     * @param menuId
     */
    void allotPowerMenu(Integer powerId, Integer menuId);

    /**
     * 菜单权限删除
     *
     * @param powerId
     * @param menuId
     */
    void deletePowerMenu(Integer powerId, Integer menuId);


    /**
     * 权限访问分配
     *
     * @param powerId
     * @param accessId
     */
    void allotPowerAccess(@Param("powerId") Integer powerId, @Param("accessId") Integer accessId);

    /**
     * 访问权限删除
     *
     * @param powerId
     * @param accessId
     */
    void deletePowerAccess(Integer powerId, Integer accessId);


    void deletePowerAccessByPowerId(Integer powerId);

    /**
     * 判断用户访问权限
     *
     * @return
     * @throws Exception
     */
    int getUserAccess(@Param("userId") Integer userId, @Param("url") String url, @Param("urlMethod") String urlMethod);


    /**
     * 获取用户访问权限
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> getUserAccessUrl(@Param("userId") Integer userId);


    /**
     * 用户访问权限添加
     *
     * @param access
     * @return
     */
    int addAccessUrl(Map<String, Object> access);


    /**
     * 清空用户访问权限
     */
    void clearAccessUrl();

}
