package com.atinbo.webmvc.controller;


import com.atinbo.core.exception.HttpApiException;
import com.atinbo.core.model.GatewayUser;
import com.atinbo.webmvc.resolver.SessionUserResolver;
import com.lujia.model.StatusCodeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于网关控制类
 *
 * @author breggor
 */
@Slf4j
public abstract class GatewayController {
    private static final String ENV_STRICT_KEY = "strict_mode";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;


    protected GatewayUser getSessionUser() throws HttpApiException {
        return this.getSessionUser(this.request);
    }

    protected GatewayUser getSessionUser(GatewayUser defaultUser) throws HttpApiException {
        return this.getSessionUser(this.request, defaultUser);
    }

    protected GatewayUser getSessionUser(HttpServletRequest request) throws HttpApiException {
        return this.getSessionUser(request, GatewayUser.ANONYMOUS);
    }

    @SneakyThrows
    protected GatewayUser getSessionUser(HttpServletRequest request, GatewayUser defaultUser) throws HttpApiException {
        GatewayUser user = null;
        try {
            user = SessionUserResolver.getSessionUser(request);
        } catch (Exception ex) {
            //环境变量获取参数：是否开启默认用户
            String strictEvnValue = System.getenv(ENV_STRICT_KEY);
            boolean isStrictMode = !StringUtils.isEmpty(strictEvnValue);
            if (isStrictMode) {
                throw new HttpApiException(StatusCodeEnum.UN_AUTHORIZED);
            }
            user = defaultUser;
        }
        return user;
    }
}
