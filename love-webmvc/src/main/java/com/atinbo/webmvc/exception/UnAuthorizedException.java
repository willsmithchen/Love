package com.atinbo.webmvc.exception;

import com.atinbo.common.StringPool;
import org.springframework.http.HttpStatus;


/**
 * 未授权异常
 *
 * @author breggor
 */
public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = -49287562885361801L;

    public UnAuthorizedException(String msg) {
        super(msg);
    }

    public UnAuthorizedException(HttpStatus status, String msg) {
        super(status.toString() + StringPool.COLON + msg);
    }
}
