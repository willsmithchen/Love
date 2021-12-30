package com.atinbo.webmvc.exception;


import com.lujia.exception.BizException;

/**
 * 没有找到用户异常
 *
 * @author breggor
 */
public class UserNotFoundException extends BizException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
