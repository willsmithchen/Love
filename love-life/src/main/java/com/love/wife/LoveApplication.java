package com.love.wife;

import com.atinbo.core.AtinboCoreConfig;
import com.atinbo.core.spring.StartupHelper;
import com.love.wife.config.MinIoConfig;
import com.love.wife.model.baidu.BaiduYaml;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/8 1:05
 * 爱的启动类
 */
@Slf4j
@EnableSwagger2
@EnableDiscoveryClient
@MapperScan(value = "com.love.wife.mapper")
@EnableConfigurationProperties({BaiduYaml.class, MinIoConfig.class})
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(scanBasePackageClasses = {LoveApplication.class, AtinboCoreConfig.class})
public class LoveApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(LoveApplication.class, args);
        StartupHelper.printStartInfo(log, ctx);
    }

}
