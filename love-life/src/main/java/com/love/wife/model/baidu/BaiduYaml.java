package com.love.wife.model.baidu;

import com.baidu.aip.speech.AipSpeech;
import com.love.wife.utils.baidu.TokenHolder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/1 17:16
 * 百度关键字提示
 */

@Data
@ConfigurationProperties(prefix = "baidu")
@ApiModel(value = "百度关键字提示参数")
public class BaiduYaml {
    @ApiModelProperty(value = "APPID")
    private String appId;
    @ApiModelProperty(value = "API密钥")
    private String apiKey;
    @ApiModelProperty(value = "密钥")
    private String secretKey;
    @ApiModelProperty(value = "网络连接超时时间")
    private Integer connectionTimeoutInMillis;
    @ApiModelProperty(value = "网络超时时间")
    private Integer socketTimeoutInMillis;
    @ApiModelProperty(value = "是否验证")
    private Boolean isAuthorized;
    @ApiModelProperty(value = "语音调用地址")
    private String url;

    @Bean
    public AipSpeech getAipClient() {
        AipSpeech client = new AipSpeech(appId, apiKey, secretKey);
        client.setConnectionTimeoutInMillis(connectionTimeoutInMillis);
        client.setSocketTimeoutInMillis(socketTimeoutInMillis);
        return client;
    }

    /**
     * 封装token处理
     *
     * @return
     * @throws Exception
     */
    @Bean
    public TokenHolder getTokenHolder() throws Exception {
        TokenHolder tokenHolder = new TokenHolder(apiKey, secretKey, TokenHolder.ASR_SCOPE);
        tokenHolder.resfresh();
        return tokenHolder;
    }


}
