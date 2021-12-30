package com.love.wife.utils.baidu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/11 11:26
 * 封装httpClient响应结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpClientResult implements Serializable {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(int code) {
        this.code = code;
    }
}
