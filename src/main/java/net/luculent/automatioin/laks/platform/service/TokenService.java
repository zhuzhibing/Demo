package net.luculent.automatioin.laks.platform.service;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.luculent.automatioin.laks.platform.entity.UserEntity;

import java.util.Map;

public interface TokenService {

    String create(UserEntity user, String channel);

    String create(String userId, String userName, String channel);

    Map<String, Claim> decoded(String token);

}
