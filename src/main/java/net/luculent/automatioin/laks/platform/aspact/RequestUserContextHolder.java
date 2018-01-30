package net.luculent.automatioin.laks.platform.aspact;

import net.luculent.automatioin.laks.platform.entity.UserEntity;
import org.springframework.core.NamedInheritableThreadLocal;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 15:33 2018/1/26
 * @Modified By:
 */
public abstract class RequestUserContextHolder {

    private static final ThreadLocal<UserEntity> inheritableUserHolder = new NamedInheritableThreadLocal<UserEntity>("Request UserEntity");

    public static void destroy() {
        inheritableUserHolder.remove();
    }

    public static UserEntity getRequestLoginUser() {
        return inheritableUserHolder.get();
    }

    public static void setRequestLoginUser(UserEntity user) {
        inheritableUserHolder.set(user);
    }


}

