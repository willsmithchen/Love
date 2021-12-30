package com.lujia.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zenghao
 * @date 2019-07-18
 */
@EnableSwaggerBootstrapUi
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnBean(annotation = EnableSwagger2.class)
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {
    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis((Predicate<RequestHandler>) RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(Predicates.not(Predicates.or(buildExcludePredicates())))
                .build().securityContexts(buildSecurityContexts()).securitySchemes(buildApiKeys());
    }

    // 忽略路径
    private List<Predicate<String>> buildExcludePredicates() {
        List<Predicate<String>> exclude = new ArrayList<>();
        if (!CollectionUtils.isEmpty(swaggerProperties.getExcludePaths())) {
            List<Predicate<String>> list = new ArrayList<>();
            for (String s : swaggerProperties.getExcludePaths()) {
                Predicate<String> ant = PathSelectors.ant(s);
                list.add((Predicate<String>) ant);
            }
            exclude = list;
        }
        return exclude;
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .build();
    }

    private List<SecurityScheme> buildApiKeys() {
        List<SecurityScheme> result = Lists.newArrayList();
        result.add(new ApiKey("Authorization", "Authorization", "header"));
        if (null != swaggerProperties.getApiKeys() && !swaggerProperties.getApiKeys().isEmpty()) {
            swaggerProperties.getApiKeys().forEach(o -> {
                result.add(new ApiKey(o.getName(), o.getKeyname(), o.getPassAs()));
            });
        }
        return result;
    }

    private List<SecurityContext> buildSecurityContexts() {
        List<SecurityContext> result = Lists.newArrayList();
        SecurityContext sc = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();

        return result;
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = Lists.newArrayList();
        AuthorizationScope[] authScopes = new AuthorizationScope[]{new AuthorizationScope("global", "全局设置")};

        buildApiKeys().forEach(o -> {
            result.add(new SecurityReference(o.getName(), authScopes));
        });
        return result;
    }
}