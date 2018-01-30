package net.luculent.automatioin.laks.platform.aspact;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.luculent.automatioin.laks.platform.domain.Result;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description token验证
 * Author zhuzb
 * CreateTime 2018-01-22 18:00
 **/
@Aspect
@Component
public class TokenVerifyAspect {

    private static final Logger logger = LoggerFactory.getLogger(TokenVerifyAspect.class);

    /**
     * token存活时间
     */
    @Value("${laks.jwt.expires-time}")
    private Integer expiresTime;

    /**
     * token续权时间
     */
    @Value("${laks.jwt.update-interval}")
    private Integer updateInterval;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private TokenService tokenService;

    /**
     * 对实现TokenCheck类进行token验证
     */
    @Pointcut("execution(* net.luculent.automatioin.laks.platform.controller.TokenController+.*(..))")
    public void serviceTokenVerify() {
    }

    /**
     * 环绕处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("serviceTokenVerify()")
    public Object doAround(ProceedingJoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //  获取token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return new Result(21001, "token missing");
        }

        // 判断token是否合法
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256("luculent@2018_partycons");
        } catch (UnsupportedEncodingException e) {
            logger.error(" == >> ",e.getMessage());
            return new Result(21004, "invalid token");
        }
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("luculent@automation").build();
        DecodedJWT jwt = verifier.verify(token);

        // 获取有效载荷 playload
        Map<String, Claim> jwtClaims = jwt.getClaims();
        long userId = jwtClaims.get("userId").asLong();
        String channel = jwtClaims.get("channel").asString();

        // 构建redis token key
        String userKey = "token_" + channel + "_" + userId;
        // 查看剩余时间
        long time = redisTemplate.getExpire(userKey);
        if (time < 1) {
            return new Result(21002, "token expiring");
        }

        Object redisToken = redisTemplate.opsForValue().get(userKey);
        if (redisToken == null) {
            return new Result(21003, "token not exist");
        }
        if (!token.equals(redisToken)) {
            // 判断同一用户同一渠道多次登陆
            return new Result(21004, "invalid token");
        }

        if (time < updateInterval) {
            // token续权
            redisTemplate.expire(userKey, expiresTime, TimeUnit.SECONDS);
        }

        // 获取redis里的用户信息存入ThreadLocal
        Object user = redisTemplate.opsForValue().get("user_info_" + userId);
        RequestUserContextHolder.setRequestLoginUser((UserEntity) user);

        /** 执行目标方法 **/
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }

        return result;
    }


}
