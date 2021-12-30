package com.atinbo.security.config;


import com.atinbo.security.filter.JwtAuthenticationTokenFilter;
import com.atinbo.security.handler.BaseAuthenticationEntryPoint;
import com.atinbo.security.handler.BaseLogoutSuccessHandler;
import com.atinbo.security.service.BaseUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * web权限配置类
 *
 * @author breggor
 */
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ANON_PATHS = {"/login", "/logout", "/captcha/image"};
    //    private static final String[] ALLOW_VISIT_PATHS = {"/*.ico", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js",
//            "/profile/**", "/actuator/**", "/druid/**", "/swagger-ui.html", "/*/api-docs", "/webjars/**", "/swagger-resources/**"};
    private static final String[] ALLOW_VISIT_PATHS = {"/*.ico", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js",
            "/profile/**", "/actuator/**", "/druid/**", "/doc.html", "/*/api-docs", "/webjars/**", "/swagger-resources/**"};

    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private BaseUserDetailsService userDetailsService;


    /**
     * 多个路径逗号分隔
     */
    @Value("${security.allowPath:''}")
    private String allowPath;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        String[] allowPathArr = ALLOW_VISIT_PATHS;
        if (!StringUtils.isEmpty(this.allowPath)) {
            String[] arr = this.allowPath.split(",");
            allowPathArr = new String[arr.length + ALLOW_VISIT_PATHS.length];

            System.arraycopy(ALLOW_VISIT_PATHS, 0, allowPathArr, 0, ALLOW_VISIT_PATHS.length);
            System.arraycopy(arr, 0, allowPathArr, ALLOW_VISIT_PATHS.length, arr.length);
        }
        log.info("web security allow paths={}", String.join(",", allowPathArr));

        httpSecurity.cors().and()
                // CRSF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler()).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(allowPathArr).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(ANON_PATHS).anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable();

        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler());
        httpSecurity.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        Objects.requireNonNull(userDetailsService, "userDetailsService 为空，请项目中实现  BaseUserDetailsService.class");
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(this.userDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证失败处理类
     */
    @Bean
    @ConditionalOnMissingBean
    public BaseAuthenticationEntryPoint unauthorizedHandler() {
        return new BaseAuthenticationEntryPoint(objectMapper());
    }

    /**
     * 退出处理类
     */
    @Bean
    @ConditionalOnMissingBean
    public BaseLogoutSuccessHandler logoutSuccessHandler() {
        return new BaseLogoutSuccessHandler(objectMapper());
    }

    /**
     * token认证过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}