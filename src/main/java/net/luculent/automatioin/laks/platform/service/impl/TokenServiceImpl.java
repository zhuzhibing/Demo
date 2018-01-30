package net.luculent.automatioin.laks.platform.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import net.luculent.automatioin.laks.platform.entity.UserEntity;
import net.luculent.automatioin.laks.platform.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

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

    @Value("${laks.jwt.issuer}")
    private String issuer;

    @Value("${laks.jwt.secret}")
    private String secret;

    @Override
    public String create(UserEntity user, String channel) {
        Algorithm algorithm = null;  //秘钥算法
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String token = JWT.create()
                .withIssuer(issuer)  // 发行者
                .withClaim("channel", channel)   // 登录渠道
                .withClaim("userId", user.getUserId())   // 用户id
                .withClaim("createTime", System.currentTimeMillis())  //生成时间
                .sign(algorithm); //签名

        return token;
    }

    @Override
    public Map<String, Claim> decoded(String token) {

        return Optional.ofNullable(token)
                .map(
                        t -> {
                            try {
                                // 判断token是否合法
                                Algorithm algorithm = Algorithm.HMAC256(secret);
                                JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
                                return verifier.verify(token);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                return null;
                            }

                        }
                ).map(
                        jwt -> jwt.getClaims() // 获取有效载荷 playload
                ).orElse(null);

    }

    @Override
    public Integer getTokenExpiresTime() {
        return this.expiresTime;
    }

    @Override
    public Integer getTokenUpdateInterval() {
        return this.updateInterval;
    }
}
