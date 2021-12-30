package com.atinbo.core.exception;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/3 15:08
 * <p>
 * 请求参数错误异常
 */

public class RequestParamException extends IllegalArgumentException {

    public RequestParamException(String message) {
        super(message);
    }

    public RequestParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParamException(Throwable cause) {
        super(cause);
    }
}
