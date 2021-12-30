package com.atinbo.webmvc.exception;

/**
 * 自定义异常
 * 请使用 atinbo-webmvc模块里的异常类
 *
 * @author breggor
 */
public class DataNotFoundException extends SecurityBaseException {
    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String module, String code, Object[] args, String error) {
        super(module, code, args, error);
    }

    public DataNotFoundException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public DataNotFoundException(String module, String error) {
        super(module, error);
    }

    public DataNotFoundException(String code, Object[] args) {
        super(code, args);
    }

    public DataNotFoundException(String error) {
        super(error);
    }
}