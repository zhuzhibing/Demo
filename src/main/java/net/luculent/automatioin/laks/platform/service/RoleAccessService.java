package net.luculent.automatioin.laks.platform.service;

import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 15:28 2018/2/28
 * @Modified By:
 */
public interface RoleAccessService {


    /**
     * 用户角色分配
     * @param userId
     * @param roleId
     */
    void allotUserRole(Integer userId,Integer roleId);

    /**
     * 用户角色删除
     * @param userId
     * @param roleId
     */
    void deleteUserRole(Integer userId,Integer roleId);


    /**
     * 角色权限分配
     * @param roleId
     * @param powerId
     */
    void allotRolePower(Integer roleId,Integer powerId);

    /**
     * 角色权限删除
     * @param roleId
     * @param powerId
     */
    void deleteRolePower(Integer roleId,Integer powerId);


    /**
     * 菜单权限分配
     * @param powerId
     * @param menuId
     */
    void allotPowerMenu(Integer powerId,Integer menuId);

    /**
     * 菜单权限删除
     * @param powerId
     * @param menuId
     */
    void deletePowerMenu(Integer powerId,Integer menuId);


    /**
     * 权限访问分配
     * @param powerId
     * @param accessId
     */
    void allotPowerAccess(Integer powerId,Integer accessId);

    /**
     * 访问权限删除
     * @param powerId
     * @param accessId
     */
    void deletePowerAccess(Integer powerId,Integer accessId);



    /**
     * 判断用户访问权限
     * @return
     * @throws Exception
     */
    boolean isAllowAccess(Integer userId, String url,String urlMethod);


    /**
     * 获取用户访问权限
     * @return
     * @throws Exception
     */
    Map<String,Object> getUserAccessUrl(Integer userId);


    /**
     * 用户访问权限添加
     * @param url
     * @return
     */
    int addAccessUrl(String url,String method);


    /**
     * 清空用户访问权限
     */
    void  clearAccessUrl();

}
