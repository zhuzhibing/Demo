package net.luculent.automatioin.laks.platform.interceptor;

import com.auth0.jwt.interfaces.Claim;
import net.luculent.automatioin.laks.platform.BasicHttpHandler;
import net.luculent.automatioin.laks.platform.aspact.RequestUserContextHolder;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 14:07 2018/3/12
 * @Modified By:
 */
@Component
public class TokenVerifyInterceptor extends BasicHttpHandler implements HandlerInterceptor {



    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //  获取token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            this.sendJsonMessage(response, new Result(21001, "token missing"));
            return false;
        }

        // 判断token是否合法
        Map<String, Claim> jwtClaims = tokenService.decoded(token);
        if (jwtClaims == null) {
            this.sendJsonMessage(response, new Result(21004, "invalid token"));
            return false;
        }

        long userId = jwtClaims.get("userId").asLong();
        String channel = jwtClaims.get("channel").asString();

        // 构建redis token key
        String userKey = "token_" + channel + "_" + userId;
        // 查看剩余时间
        long time = redisTemplate.getExpire(userKey);
        if (time < 1) {
            this.sendJsonMessage(response, new Result(21002, "token expiring"));
            return false;
        }

        Object redisToken = redisTemplate.opsForValue().get(userKey);
        if (redisToken == null) {
            this.sendJsonMessage(response, new Result(21003, "token not exist"));
            return false;
        }
        if (!token.equals(redisToken)) {
            // 判断同一用户同一渠道多次登陆
            this.sendJsonMessage(response, new Result(21004, "invalid token"));
            return false;
        }

        if (time < tokenService.getTokenUpdateInterval()) {
            // token续权
            redisTemplate.expire(userKey, tokenService.getTokenExpiresTime(), TimeUnit.SECONDS);
        }

        // 获取redis里的用户信息存入ThreadLocal
        Object user = redisTemplate.opsForValue().get("user_info_" + userId);
        RequestUserContextHolder.setRequestLoginUser((UserEntity) user);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }



}
