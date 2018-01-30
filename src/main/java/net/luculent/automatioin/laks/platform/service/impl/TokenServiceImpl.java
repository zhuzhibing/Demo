package net.luculent.automatioin.laks.platform.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    /**
     * token存活时间
     */
    @Value("${laks.jwt.expires-time}")
    private Integer expiresTime;

    @Resource(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate;

    @Override
    public String create(UserEntity user, String channel) {
        Algorithm algorithm = null;  //秘钥算法
        try {
            algorithm = Algorithm.HMAC256("luculent@2018_partycons");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String token = JWT.create()
                .withIssuer("luculent@automation")  // 发行者
                .withClaim("channel", channel)   // 登录渠道
                .withClaim("userId", user.getUserId())   // 用户id
                .withClaim("createTime", System.currentTimeMillis())  //生成时间
                .sign(algorithm); //签名

        // 构建redis token key
        String userKey = "token_" + channel + "_" + user.getUserId();
        this.redisTemplate.opsForValue().set(userKey, token);
        // 设置token有效时间
        redisTemplate.expire(userKey, expiresTime, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("user_info_" + user.getUserId(), user);

        return token;
    }

    @Override
    public String create(String userId, String userName, String channel) {

        Algorithm algorithm = null;  //秘钥算法
        try {
            algorithm = Algorithm.HMAC256("luculent@2018_partycons");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String token = JWT.create()
                .withIssuer("luculent@automation")  // 发行者
                .withClaim("channel", channel)   // 登录渠道
                .withClaim("userId", userId)   // 用户id
                .withClaim("userName", userName)   // 用户id
                .withClaim("createTime", System.currentTimeMillis())  //生成时间
                .sign(algorithm); //签名

        // 构建redis token key
        String userKey = "token_" + channel + "_" + userId;
        this.redisTemplate.opsForValue().set(userKey, token);
        // 设置token有效时间
        redisTemplate.expire(userKey, expiresTime, TimeUnit.SECONDS);

        return token;
    }

    @Override
    public Map<String, Claim> decoded(String token) {

        Optional<Map<String, Claim>> jwtClaims = Optional.ofNullable(token)
                .map(
                        t -> {
                            // 判断token是否合法
                            Algorithm algorithm = null;
                            try {
                                algorithm = Algorithm.HMAC256("luculent@2018_partycons");
                            } catch (UnsupportedEncodingException e) {
//                      logger.error(" == >> ",e.getMessage());
                            }
                            JWTVerifier verifier = JWT.require(algorithm).withIssuer("luculent@automation").build();
                            return verifier.verify(token);
                        }
                ).map(
                        jwt -> jwt.getClaims() // 获取有效载荷 playload
                );

        return jwtClaims.get();
    }
}
