package net.luculent.automatioin.laks.platform.controller;

import net.luculent.automatioin.laks.platform.aspact.RequestUserContextHolder;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.enums.SysExpEnum;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 11:42 2018/1/5
 * @Modified By:
 */
public class BaseController {


    @Resource(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate;

    /**
     * 返回成功(含参)
     *
     * @param object
     * @return
     */
    public Result success(Object object) {
        int code = SysExpEnum.SUCCESS.getCode();
        String msg = SysExpEnum.SUCCESS.getMessage();
        Result result = message(code, msg);
        result.setData(object);

        return result;
    }


    /**
     * 返回成功
     *
     * @return
     */
    public Result success() {
        return success(null);
    }

    /**
     * 返回信息
     *
     * @param code
     * @param msg
     * @return
     */
    public Result message(Integer code, String msg) {
        Result result = new Result(code, msg);
        return result;
    }

    /**
     * 返回失败
     *
     * @return
     */
    public Result error() {
        int code = SysExpEnum.UNKNOW_ERROR.getCode();
        String msg = SysExpEnum.UNKNOW_ERROR.getMessage();
        Result result = message(code, msg);

        return result;
    }

    /**
     * 创建新增数据
     * @return
     */
    public Map<String, Object> createData(){

        return null;

    }


    /**
     * 获取登录用户信息
     * @return
     */
    public UserEntity getUser(){

        return RequestUserContextHolder.getRequestLoginUser();
    }

}
