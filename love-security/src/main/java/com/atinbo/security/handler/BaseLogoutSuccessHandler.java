package com.atinbo.security.handler;


import com.atinbo.security.model.LoginUser;
import com.atinbo.security.service.UserTokenService;
import com.atinbo.webmvc.utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujia.model.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义退出处理类 返回成功
 *
 * @author breggor
 */
@Slf4j
public class BaseLogoutSuccessHandler implements LogoutSuccessHandler {
    private final ObjectMapper objectMapper;

    @Autowired
    private UserTokenService userTokenService;

    public BaseLogoutSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = userTokenService.getLoginUser(request);
        if (!Objects.isNull(loginUser)) {
            userTokenService.removeToken(loginUser);
            // 记录用户退出日志
            log.info("{} -- 退出成功", loginUser.getUsername());
        }
        WebUtil.renderString(response, objectMapper.writeValueAsString(Outcome.success().setMessage("退出成功")));
    }
}
