package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "project-url")
public class ProjectUrlConfig {
    private String wechatMpAuthorize;
    private String wechatOpenAuthorize;
    private String sell;
}
