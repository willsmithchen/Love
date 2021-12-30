package com.atinbo.core.exception;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/3 14:30
 */

public class BizException extends BaseException{

    private static final long serialVersionUID = 1L;

    public BizException(String module, String code, Object[] args, String error) {
        super(module, code, args, error);
    }

    public BizException(String module, String code, Object[] args) {
        super(module, code, args);
    }

    public BizException(String module, String error) {
        super(module, error);
    }

    public BizException(String code, Object[] args) {
        super(code, args);
    }

    public BizException(String error) {
        super(error);
    }
}
