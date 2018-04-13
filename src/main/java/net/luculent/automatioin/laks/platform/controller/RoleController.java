package net.luculent.automatioin.laks.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 16:04 2018/3/15
 * @Modified By:
 */
@RestController
@RequestMapping(value = "/platform/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色查询
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    public Result get(@RequestParam Integer id) throws Exception {

        Map<String, Object> role = roleService.queryOne(id);
        Result result = this.success(role);

        return result;

    }


    /**
     * 角色保存
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public Result put(@RequestBody JSONObject body) throws Exception {

        String roleName = body.getString("roleName");
        String roleNumber = body.getString("roleNumber");
        String description = body.getString("description");

        Map<String, Object> role = new HashMap<String, Object>();
        role.put("roleName", roleName);
        role.put("roleNumber", roleNumber);
        role.put("description", description);

        Integer userId = this.getUser().getUserId();
        if(body.containsKey("roleSeq")){
            // 修改
            Integer roleSeq = body.getInteger("roleSeq");
            role.put("roleSeq", roleSeq);
            role.put("lstusrId", userId);
            role.put("lstusrDtm", LocalDateTime.now());
            roleService.add(role);
        }else{
            // 新增
            role.put("fstusrId", userId);
            role.put("fstusrDtm", LocalDateTime.now());
            roleService.modify(role);
        }

        return this.success();

    }

    /**
     * 角色删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) throws Exception {
        roleService.delete(id);
        return this.success();
    }


    /**
     * 角色分页查询
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/getPageInfo")
    public Result getPageInfo(@RequestBody JSONObject body) throws Exception {
        String roleName = body.getString("roleName");

        JSONObject pageInfo = body.getJSONObject("pageInfo");

        int pageNo = pageInfo.getInteger("pageNo");

        int pageSize = pageInfo.getInteger("pageSize");

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("roleName", roleName);

        PageInfo result = roleService.queryPageInfo(condition, pageNo, pageSize);

        return this.success(result);
    }


}
