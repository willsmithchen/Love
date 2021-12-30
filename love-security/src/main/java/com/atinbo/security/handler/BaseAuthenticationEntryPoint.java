package com.atinbo.security.handler;


import com.lujia.model.Outcome;
import com.lujia.model.StatusCodeEnum;
import com.atinbo.webmvc.utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类 返回未授权
 *
 * @author breggor
 */
@AllArgsConstructor
public class BaseAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final long serialVersionUID = 1L;
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String msg = String.format("请求访问：%s，%s: 认证失败，无法访问系统资源", request.getRequestURI(), HttpStatus.UNAUTHORIZED);
        WebUtil.renderString(response, objectMapper.writeValueAsString(Outcome.failure(StatusCodeEnum.UN_AUTHORIZED, msg)));
    }
}
