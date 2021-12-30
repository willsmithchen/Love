package com.atinbo.webmvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 跨域配置属性
 *
 * @author Breggor
 */
@Data
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private String pathPattern;
    private String[] allowedHeaders;
    private String[] allowedMethods;
    private String[] allowedOrigins;
    private boolean allowCredentials;
    private long maxAge;
}
