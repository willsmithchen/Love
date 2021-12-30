package com.atinbo.core.exception;


import com.lujia.model.StatusCode;

/**
 * 接口异常
 * 请使用 atinbo-webmvc模块里的异常类
 *
 * @author breggor
 */
@Deprecated
public class HttpApiException extends RuntimeException {

    protected StatusCode status;


    public HttpApiException(StatusCode statusCode) {
        this.status = statusCode;
    }

    public StatusCode getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "APIException{http=" + this.status + '}';
    }
}
