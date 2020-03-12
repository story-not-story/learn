package com.imooc.sell.aspect;

import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.UserException;
import com.imooc.sell.util.CookieUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Pointcut("execution(public * com.imooc.sell.controller.Seller*.*(..))" + "&& !execution(public * com.imooc.sell.controller.SellerUser*.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Cookie cookie = CookieUtil.get(attributes.getRequest(), CookieUtil.TOKEN);
        if (cookie == null){
            log.error("【用户cookie校验】cookie没有token");
            throw new UserException(ErrorCode.OWNER_ERROR);
        }
        String redisValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN, cookie.getValue()));
        if (StringUtils.isNullOrEmpty(redisValue)){
            log.error("【用户cookie校验】redis没有token");
            throw new UserException(ErrorCode.OWNER_ERROR);
        }
    }
}
