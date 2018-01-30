package net.luculent.automatioin.laks.platform.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisOperation {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * String类型存入redis
     * @param key
     * @param value
     */
    public void setString2Redis(String key, String value) {

        stringRedisTemplate.opsForValue().set(key, value);

    }

    public void setString2Redis(String key, String value, long time) {

        if (time > 0) {
            stringRedisTemplate.opsForValue().set(key, value);
            expire(key, time);
        }

    }

    /**
     * 根据key获取String类型
     * @param key
     * @return
     */
    public String getStringFromRedis(String key) {

        return stringRedisTemplate.opsForValue().get(key);

    }

    /**
     * 指定缓存失效时间
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {

        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 清除redis缓存
     * @param key
     */
    public void delete(String key) {

        stringRedisTemplate.delete(key);

    }


}
