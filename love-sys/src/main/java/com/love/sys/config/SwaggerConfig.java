package com.love.sys.config;

import io.github.yedaxia.apidocs.DocsConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 20:01
 */

@Data
@ConfigurationProperties(prefix = "swagger.docs.config")
public class SwaggerConfig {
    /**
     * 项目根目录
     */
    private String projectPath;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 声明该API的版本
     */
    private String apiVersion;
    /**
     * 生成API文档所在目录
     */
    private String docsPath;
    /**
     * 是否自动生成文档
     */
    private Boolean autoGenerate;

    @Bean(value = "swagger")
    public DocsConfig getSwagger() {
        DocsConfig config = new DocsConfig();
        config.setProjectPath(projectPath);
        config.setProjectName(projectName);
        config.setApiVersion(apiVersion);
        config.setDocsPath(docsPath);
        config.setAutoGenerate(autoGenerate);
        return config;
    }
}
