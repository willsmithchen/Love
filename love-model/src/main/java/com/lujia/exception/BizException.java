package com.lujia.exception;


/**
 * 系统业务异常
 * 说明：异常内容抛出
 *
 * @author breggor
 */
public class BizException extends RuntimeException {
    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}