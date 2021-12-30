package com.atinbo.core.consts;

import lombok.experimental.UtilityClass;

/**
 * 网关常量
 *
 * @author breggor
 */
@UtilityClass
public class CoreConsts {

    /**
     * 网关header字段：认证code
     */
    public static final String GW_AUTH_TOKEN = "GW-AUTH-TOKEN";

    /**
     * 网关header字段：用户Id
     */
    public static final String GW_USER_ID = "GW-USER-ID";

    /**
     * 网关header字段：用户名称
     */
    public static final String GW_USER_NAME = "GW-USER-NAME";

    /**
     * 网关header字段：用户信息，urlencode
     */
    public static final String GW_USER_ENCODED = "GW-USER-ENCODED";

    /**
     * 网关header字段：租户Id，目前是企业Id
     */
    public static final String GW_TENANT_ID = "GW-TENANT-ID";

    /**
     * 网关header字段：平台ID
     */
    public static final String GW_PLAT_ID = "GW-PLAT-ID";
}
