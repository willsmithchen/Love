package com.atinbo.webmvc.resolver;


import com.atinbo.common.Charsets;
import com.atinbo.core.model.GatewayUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

import static com.atinbo.core.consts.CoreConsts.*;


/**
 * 网关用户解析
 *
 * @author breggor
 */
@Slf4j
@UtilityClass
public class GatewayResolver {
    public static final String GW_REQUEST_ID = "GW-REQUEST-ID";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * 请求头获取用户ID
     *
     * @param request
     * @return String
     */
    public static Long getGatewayUserId(HttpServletRequest request) {
        return Long.parseLong(request.getHeader(GW_USER_ID));
    }

    /**
     * 请求头获取用户名
     *
     * @param request
     * @return String
     */
    public static String getGatewayUserName(HttpServletRequest request) {
        return request.getHeader(GW_USER_NAME);
    }

    /**
     * 请求头获取用户信息
     *
     * @param request
     * @return GatewayUser
     */
    public static GatewayUser getGatewayUser(HttpServletRequest request) {
        String userEncode = request.getHeader(GW_USER_ENCODED);
        if (StringUtils.isNotBlank(userEncode)) {
            try {
                String userToken = URLDecoder.decode(userEncode, Charsets.UTF8_NAME);
                return OBJECT_MAPPER.readValue(userToken, GatewayUser.class);
            } catch (Exception ex) {
                log.debug("[GW-USER-ENCODED] -- JSON解析异常", ex);
            }
        }
        return null;
    }

    /**
     * 请求头获取平台Id
     *
     * @param request
     * @return String
     */
    public static String getGatewayPlatId(HttpServletRequest request) {
        return request.getHeader(GW_PLAT_ID);
    }
}
