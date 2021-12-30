package com.atinbo.webmvc.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * RequestId与耗时记录拦截
 *
 * @author breggor
 */
@Slf4j
@Component
public class RequestIdFilter extends OncePerRequestFilter {
    private static final String REQUEST_ID = "requestId";

    private final ThreadLocal<Long> actTime = new InheritableThreadLocal<>();

    private void before(HttpServletRequest request) {
        actTime.set(System.currentTimeMillis());
        String requestId = request.getHeader(REQUEST_ID);
        log.info("Header: requestId({})", requestId);
        if (!StringUtils.isEmpty(requestId)) {
            requestId = request.getParameter(REQUEST_ID);
            log.info("Body: requestId({})", requestId);
        }
        if (StringUtils.isEmpty(requestId)) {
            requestId = System.currentTimeMillis() + "";
        }
        MDC.put(REQUEST_ID, requestId);
        log.info(MessageFormat.format("request path={0}", request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        before(request);

        chain.doFilter(request, response);

        after(request);
    }


    private void after(HttpServletRequest request) {
        long consumeTime = System.currentTimeMillis() - actTime.get();
        log.info("request path={}, Elapsed time:{} (ms)", request.getRequestURI(), consumeTime);
        MDC.clear();
        actTime.remove();
    }
}