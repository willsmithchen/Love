package com.atinbo.webmvc.exception;



/**
 * 用户信息异常类
 *
 * @author breggor
 */
public class UserException extends SecurityBaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
