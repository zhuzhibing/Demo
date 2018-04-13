package net.luculent.automatioin.laks.platform.controller;

import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.service.RoleAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 13:56 2018/2/26
 * @Modified By:
 */
@RestController
@RequestMapping(value = "/platform")
public class RoleAccessController extends BaseController{

    @Autowired
   private RoleAccessService roleAccessService;

    /**
     * 获取访问菜单
     * @return
     * @throws Exception
     */
    @GetMapping("/getMenusByUser")
    public Result getMenuInfo() throws Exception{

        Integer userId = this.getUser().getUserId();

        // 获取用户访问的菜单
        Map<String,Object> menuList=  roleAccessService.getUserAccessUrl(userId);

        Result result = this.success(menuList);

        return result;


    }

    /**
     * 用户角色分配
     * @return
     * @throws Exception
     */
    @PostMapping("/allotUserRole")
    public Result allotUserRole(String userId,String roleId) throws Exception{



        // 获取用户信息
        //Allot userRole


        // 根据登录用户id获取用户权限信息


        return null;


    }

    /**
     * 角色权限分配
     * @return
     * @throws Exception
     */
    public Result allotRoleJurisdiction() throws Exception{

        // 获取用户信息

        // 根据登录用户id获取用户权限信息


        return null;


    }

    /**
     * 权限菜单分配
     * @return
     * @throws Exception
     */
    public Result allotJurisdictionMenu() throws Exception{

        // 获取用户信息

        //


        return null;


    }
}
