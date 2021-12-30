package com.atinbo.webmvc.exception;

import com.atinbo.core.utils.MessageSourceUtil;
import com.lujia.exception.BizException;
import org.apache.commons.lang3.StringUtils;

public class SecurityBaseException extends BizException {
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String error;

    public SecurityBaseException(String module, String code, Object[] args, String error) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.error = error;
    }

    public SecurityBaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public SecurityBaseException(String module, String error) {
        this(module, null, null, error);
    }

    public SecurityBaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public SecurityBaseException(String error) {
        this(null, null, null, error);
    }

    @Override
    public String getMessage() {
        String msg = null;
        if (StringUtils.isNotBlank(code)) {
            msg = MessageSourceUtil.message(code, args);
        }
        if (msg == null) {
            msg = error;
        }
        return msg;
    }

    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getError() {
        return error;
    }
}