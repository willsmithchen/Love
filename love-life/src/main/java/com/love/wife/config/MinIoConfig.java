package com.love.wife.config;

import io.minio.MinioClient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author chenlujia
 * @description Minio配置类
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinIoConfig {
    @ApiModelProperty(value = "endPoint是一个URL，域名，IPv4或者IPv6地址")
    private String endpoint;
    @ApiModelProperty(value = "TCP/IP端口号")
    private int port;
    @ApiModelProperty(value = "accessKey类似于用户ID，用于唯一标识你的账户")
    private String accessKey;
    @ApiModelProperty(value = "secretKey是你的账户的密码")
    private String secretKey;
    @ApiModelProperty(value = "如果是true，则用的是HTTPS，而不是http，默认值是true")
    private Boolean secure;
    @ApiModelProperty(value = "默认储存桶")
    private String bucketName;
    @ApiModelProperty(value = "配置目录")
    private String configDir;

    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = new MinioClient(endpoint, port, accessKey, secretKey, secure);
        return minioClient;
    }

}
