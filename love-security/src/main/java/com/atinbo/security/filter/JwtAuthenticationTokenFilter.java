package com.atinbo.security.filter;

import com.atinbo.core.utils.ObjectUtil;
import com.atinbo.security.model.LoginUser;
import com.atinbo.security.service.UserTokenService;
import com.atinbo.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token过滤器 验证token有效性
 *
 * @author breggor
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final static String LOGIN_PATH = "/login";

    @Autowired
    private UserTokenService userTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!request.getRequestURI().contains(LOGIN_PATH)) {
            LoginUser loginUser = userTokenService.getLoginUser(request);
            if (ObjectUtil.isNotEmpty(loginUser) && Objects.isNull(SecurityUtils.getAuthentication())) {
                userTokenService.verifyToken(loginUser);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}