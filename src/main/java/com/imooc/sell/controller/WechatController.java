package com.imooc.sell.controller;

import com.google.gson.JsonObject;
import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.constant.WechatUrlConstant;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.WechatException;
import com.imooc.sell.util.JsonUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@Slf4j
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WechatAccountConfig accountConfig;
    @Autowired
    private ProjectUrlConfig urlConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpService wxOpenService;
    @GetMapping("/auth")
    public String auth(@RequestParam String returnUrl) throws UnsupportedEncodingException {
        String redirect_uri = urlConfig.getWechatMpAuthorize() + "/sell/wechat/openid";
        redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
        returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
        String url = String.format(WechatUrlConstant.codeUrl, accountConfig.getMpAppId(), redirect_uri, returnUrl);
        return "redirect:" + url;
    }
    @GetMapping("/openid")
    public String openid(@RequestParam(name = "code") String code, @RequestParam(name = "state") String returnUrl){
        if (StringUtils.isNullOrEmpty(code)){
            log.error("【微信授权】获取code失败");
            throw new WechatException(ErrorCode.CODE_FAIL);
        }
        String url = String.format(WechatUrlConstant.access_tokenUrl, accountConfig.getMpAppId(), accountConfig.getMpAppSecret(), code);
        RestTemplate restTemplate = new RestTemplate();
        String jsonStr = restTemplate.getForObject(url, String.class);
        JsonObject JsonObject = JsonUtil.toObject(jsonStr);
        if (JsonObject.get("openid") == null){
            log.error("【微信授权】获取openid失败");
            throw new WechatException(ErrorCode.OPENID_FAIL);
        }
        redisTemplate.opsForValue().setIfAbsent("refresh_token", JsonObject.getAsJsonPrimitive("refresh_token").getAsString());
        return "redirect:" + returnUrl + "?openid=" + JsonObject.getAsJsonPrimitive("openid").getAsString();
    }
    @GetMapping("/refresh")
    public String refresh(){
        String refresh_token = redisTemplate.opsForValue().get("refresh_token");
        String url = String.format(WechatUrlConstant.refreshUrl, accountConfig.getMpAppId(), refresh_token);
        RestTemplate restTemplate = new RestTemplate();
        String jsonStr = restTemplate.getForObject(url, String.class);
        JsonObject JsonObject = JsonUtil.toObject(jsonStr);
        if (JsonObject.get("access_token") == null){
            log.error("【微信授权】刷新access_token失败");
            throw new WechatException(ErrorCode.REFRESH_FAIL);
        }
        redisTemplate.opsForValue().setIfPresent("refresh_token", JsonObject.getAsJsonPrimitive("refresh_token").getAsString());
        return "redirect:" + urlConfig.getSell() + "/sell" + urlConfig.getReturnUrl() + "?openid=" + JsonObject.getAsJsonPrimitive("openid").getAsString() ;
    }

    @GetMapping("/authorize")
    public String authorize(@RequestParam String returnUrl) throws UnsupportedEncodingException {
        String redirect_uri = urlConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
        returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
        String url = wxMpService.oauth2buildAuthorizationUrl(redirect_uri, WxConsts.OAuth2Scope.SNSAPI_BASE, returnUrl);
        return "redirect:" + url;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam(name = "code") String code, @RequestParam(name = "state") String returnUrl){
        if (StringUtils.isNullOrEmpty(code)){
            log.error("【微信授权】获取code失败");
            throw new WechatException(ErrorCode.CODE_FAIL);
        }
        WxMpOAuth2AccessToken jsonResult = null;
        try {
            jsonResult = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信授权】获取openid失败");
            throw new WechatException(ErrorCode.OPENID_FAIL.getCode(), e.getError().getErrorMsg());
        }
        return "redirect:" + returnUrl + "?openid=" + jsonResult.getOpenId() ;
    }
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam String returnUrl) throws UnsupportedEncodingException {
        String redirect_uri = urlConfig.getWechatMpAuthorize() + "/sell/wechat/qrUserInfo";
        returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
        String url = wxOpenService.buildQrConnectUrl(redirect_uri, WxConsts.QrConnectScope.SNSAPI_LOGIN, returnUrl);
        return "redirect:" + url;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam(name = "code") String code, @RequestParam(name = "state") String returnUrl){
        if (StringUtils.isNullOrEmpty(code)){
            log.error("【微信授权】获取code失败");
            throw new WechatException(ErrorCode.CODE_FAIL);
        }
        WxMpOAuth2AccessToken jsonResult = null;
        try {
            jsonResult = wxOpenService.oauth2getAccessToken(code);

        } catch (WxErrorException e) {
            log.error("【微信授权】获取openid失败");
            throw new WechatException(ErrorCode.OPENID_FAIL.getCode(), e.getError().getErrorMsg());
        }
        WxMpUser user = null;
        try {
            user = wxOpenService.oauth2getUserInfo(jsonResult, null);
        } catch (WxErrorException e) {
            log.error("【微信授权】获取userinfo失败");
            throw new WechatException(ErrorCode.USER_INFO_FAIL.getCode(), e.getError().getErrorMsg());
        }
        return "redirect:" + returnUrl + "?openid=" + user.getOpenId() ;
    }

}
