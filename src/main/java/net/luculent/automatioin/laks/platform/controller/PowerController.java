package net.luculent.automatioin.laks.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.service.PowerService;
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
@RequestMapping(value = "/platform/power")
public class PowerController extends BaseController {

    @Autowired
    private PowerService powerService;

    /**
     * 角色查询
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    public Result get(@RequestParam Integer id) throws Exception {

        Map<String, Object> power = powerService.queryOne(id);
        Result result = this.success(power);

        return result;

    }

    /**
     * 权限保存
     *
     * @return
     * @throws Exception
     */
    @PutMapping("/save")
    public Result save(@RequestBody JSONObject body) throws Exception {

        String powerName = body.getString("powerName");
        String powerNumber = body.getString("powerNumber");
        String description = body.getString("description");

        Map<String, Object> power = new HashMap<String, Object>();
        power.put("powerName", powerName);
        power.put("powerNumber", powerNumber);
        power.put("description", description);

        Integer userId = this.getUser().getUserId();
        if(body.containsKey("powerSeq")){
            Integer powerSeq = body.getInteger("powerSeq");
            power.put("powerSeq", powerSeq);
            power.put("lstusrId", userId);
            power.put("lstusrDtm", LocalDateTime.now());
            powerService.modify(power);
        }else{
            power.put("fstusrId", userId);
            power.put("fstusrDtm", LocalDateTime.now());
            powerService.add(power);
        }

        return this.success();

    }

    /**
     * 权限删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id) throws Exception {
        powerService.delete(id);
        return this.success();
    }

    /**
     * 权限分页查询
     *
     * @param body
     * @return
     */
    @GetMapping(value = "/getPageInfo")
    public Result getPageInfo(@RequestBody JSONObject body) throws Exception {

        String powerName = body.getString("powerName");

        JSONObject pageInfo = body.getJSONObject("pageInfo");

        int pageNo = pageInfo.getInteger("pageNo");

        int pageSize = pageInfo.getInteger("pageSize");

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("powerName", powerName);

        PageInfo result = powerService.queryPageInfo(condition, pageNo, pageSize);

        return this.success(result);
    }


}
