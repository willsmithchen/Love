package com.love.sys;

import com.atinbo.core.spring.StartupHelper;
import com.love.sys.config.SwaggerConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 19:51
 * <p>
 * 用户管理启动类
 */

@Slf4j
@EnableSwagger2
@MapperScan(value = "com.love.sys.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableConfigurationProperties({SwaggerConfig.class})
@SpringBootApplication
public class SysApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(SysApplication.class, args);
        StartupHelper.printStartInfo(log, cac);
    }
}
