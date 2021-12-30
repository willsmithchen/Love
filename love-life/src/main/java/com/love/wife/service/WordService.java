package com.love.wife.service;

import java.util.Map;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/11 10:03
 * word 服务层
 */

public interface WordService {
    /**
     * 数据列表
     *
     * @param swaggerUrl swaggerURL
     * @return
     */
    Map<String, Object> tableList(String swaggerUrl);
}
