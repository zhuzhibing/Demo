package net.luculent.automatioin.laks.platform.controller;

import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.TokenService;
import net.luculent.automatioin.laks.platform.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Description 注册登录注销账户控制器
 * Author chenmingming
 * CreateTime 2017-11-15 9:49
 **/
@RestController
@RequestMapping(value = "/public/login")
public class LoginController extends BaseController{

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;


    @RequestMapping(value = "/web/public")
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("channel") String channel) throws Exception {

        //用户名密码登录
        UserEntity user = userService.queryUser(loginName, password);
        if (user == null) {
            return this.message(21011, "error incorrect username or password");
        }

        String token = tokenService.create(user, channel);

        // 构建redis token key
        String userKey = "token_" + channel + "_" + user.getUserId();
        this.redisTemplate.opsForValue().set(userKey, token);
        // 设置token有效时间
        this.redisTemplate.expire(userKey, tokenService.getTokenExpiresTime(), TimeUnit.SECONDS);

        this.redisTemplate.opsForValue().set("user_info_" + user.getUserId(), user);

        Result result = this.success();
        result.setToken(token);
        return result;

    }
}