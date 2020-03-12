package com.imooc.sell.controller;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.entity.SellerInfo;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.UserException;
import com.imooc.sell.service.SellerInfoService;
import com.imooc.sell.util.CookieUtil;
import com.imooc.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/seller/user")
public class SellerUserController {
    @Autowired
    private SellerInfoService sellerInfoService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("user/register");
    }
    @PostMapping("/register")
    public ModelAndView register(@Valid SellerInfo sellerInfo, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【用户注册】参数错误");
            throw new UserException(ErrorCode.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String openid = sellerInfo.getOpenid();
        if (sellerInfoService.findByOpenid(openid) != null){
            log.error("【用户注册】用户已存在");
            throw new UserException(ErrorCode.USER_ALREADY_EXISTS);
        }
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfoService.save(sellerInfo);
        return new ModelAndView("user/login");
    }
    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("user/login");
    }
    @PostMapping("/login")
    public ModelAndView login(@Valid SellerInfo user,HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = CookieUtil.get(request, CookieUtil.TOKEN);
        if (cookie != null){
            log.error("【用户登录】用户已登录");
            throw new UserException(ErrorCode.USER_ALREADY_LOGIN);
        }
        String openid = user.getOpenid();
        SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
        if (sellerInfo == null){
            log.error("【用户登录】用户未注册");
            throw new UserException(ErrorCode.USER_NOT_EXISTS);
        }
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().setIfAbsent(String.format(RedisConstant.TOKEN, token), openid);
        CookieUtil.set(response, CookieUtil.TOKEN, token, CookieUtil.EXPIRE);
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(@RequestParam String openid, HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = CookieUtil.get(request, CookieUtil.TOKEN);
        if (cookie == null){
            log.error("【用户登出】用户已登出");
            throw new UserException(ErrorCode.USER_NOT_LOGIN);
        }
        String token = cookie.getValue();
        CookieUtil.set(response, CookieUtil.TOKEN, null, 0);
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN, token));
        return new ModelAndView("user/login");
    }
}
