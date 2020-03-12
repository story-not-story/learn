package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;
    private String openAppId;
    private String openAppSecret;
    private String mchId;
    private String mchKey;
    private String keyPath;
    private String notifyUrl;
    private String templateId;
}