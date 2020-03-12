package com.imooc.sell.service;

import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public boolean lock(String key, String value){
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isNullOrEmpty(currentValue) && Long.valueOf(currentValue) < System.currentTimeMillis()){
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isNullOrEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }
    public void unlock(String key, String value){
        try {
            String oldValue = stringRedisTemplate.opsForValue().get(key);
            if (!StringUtils.isNullOrEmpty(oldValue) && oldValue.equals(value)) {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e){
            log.error("redis分布式锁异常:{}", e);
        }
    }
}
