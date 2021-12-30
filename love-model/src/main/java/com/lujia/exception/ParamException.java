package com.lujia.exception;

/**
 * 请求参数错误异常
 *
 * @author breggor
 */
public class ParamException extends IllegalArgumentException {

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }
}