package com.love.sys.controller;

import com.love.sys.config.SwaggerConfig;
import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.Ignore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 21:46
 * @description
 */

@Ignore
@Controller
@Api(tags = "文档管理")
@RequestMapping(value = "/file-manager")
public class FileController {
    @Resource
    private SwaggerConfig swaggerConfig;

    /**
     * 生成swagger文档
     */
    @ApiOperation(value = "生成swagger文档")
    @GetMapping(value = "/create-swagger")
    public String createSwagger() {
        Docs.buildHtmlDocs(swaggerConfig.getSwagger());
        return "index";
    }
}
