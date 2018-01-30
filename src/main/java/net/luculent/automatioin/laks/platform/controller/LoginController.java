package net.luculent.automatioin.laks.platform.controller;

import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.TokenService;
import net.luculent.automatioin.laks.platform.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 注册登录注销账户控制器
 * Author chenmingming
 * CreateTime 2017-11-15 9:49
 **/
@RestController
@RequestMapping(value = "/platform")
public class LoginController extends BaseController{

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/login")
    public Result login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("channel") String channel) throws Exception {

        //用户名密码登录
        UserEntity user = userService.queryUser(userName, password);
        if (user == null) {
            return this.message(21011, "error incorrect username or password");
        }

        String token = tokenService.create(user, channel);

        Result result = this.success(user);
        result.setToken(token);
        return result;

    }
}