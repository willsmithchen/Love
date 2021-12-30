package com.lujia.exception;


/**
 * RPC参数校验异常
 *
 * @author breggor
 */
public class RpcParamException extends ParamException {

    public RpcParamException(String message) {
        super(message);
    }

    public RpcParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcParamException(Throwable cause) {
        super(cause);
    }

}
