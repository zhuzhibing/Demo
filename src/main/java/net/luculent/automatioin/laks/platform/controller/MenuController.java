package net.luculent.automatioin.laks.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 11:14 2018/3/7
 * @Modified By:
 */

@RestController
@RequestMapping(value = "/platform/menu")
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    /**
     * 菜单查询
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    public Result get(@RequestParam Integer id) throws Exception {

        Map<String, Object> menu = menuService.queryOne(id);
        Result result = this.success(menu);

        return result;

    }

    /**
     * 菜单保存
     *
     * @return
     * @throws Exception
     */
    @PutMapping("/save")
    public Result save(@RequestBody JSONObject body) throws Exception {

        String menuName = body.getString("menuName");
        String menuUrl = body.getString("menuUrl");
        String description = body.getString("description");

        Map<String, Object> menu = new HashMap<String, Object>();
        menu.put("menuName", menuName);
        menu.put("menuUrl", menuUrl);
        menu.put("description", description);

        Integer userId = this.getUser().getUserId();
        if(body.containsKey("menuSeq")){
            Integer menuSeq = body.getInteger("menuSeq");
            menu.put("menuSeq", menuSeq);
            menu.put("lstusrId", userId);
            menu.put("lstusrDtm", LocalDateTime.now());
            menuService.modify(menu);
        }else{
            menu.put("fstusrId", userId);
            menu.put("fstusrDtm", LocalDateTime.now());
            menuService.add(menu);
        }

        return this.success();

    }

    /**
     * 菜单删除
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id) throws Exception {
        menuService.delete(id);
        return this.success();
    }

    /**
     * 菜单分页查询
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/getPageInfo")
    public Result getPageInfo(@RequestBody JSONObject body) throws Exception {
        String menuName = body.getString("menuName");

        JSONObject pageInfo = body.getJSONObject("pageInfo");

        int pageNo = pageInfo.getInteger("pageNo");

        int pageSize = pageInfo.getInteger("pageSize");

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("menuName", menuName);

        PageInfo result = menuService.queryMenuPageInfo(condition, pageNo, pageSize);

        return this.success(result);
    }

    /**
     * 获取用户菜单
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/query")
    public Result getMenuByUser() throws Exception {

        Integer userId = this.getUser().getUserId();

        List<Map<String, String>> menuList = menuService.getMenuByUserId(userId);

        Result result = this.success(menuList);

        return result;

    }
}
