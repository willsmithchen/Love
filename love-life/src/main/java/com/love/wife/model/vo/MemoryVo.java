package com.love.wife.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/28 22:09
 * 内存出参
 */

@Data
@ApiModel(value = "内存出参")
public class MemoryVo {
    @ApiModelProperty(value = "内存总量")
    private String memory;
    @ApiModelProperty(value = "内存使用量")
    private String memRam;
    @ApiModelProperty(value = "使用中")
    private String memUsed;
    @ApiModelProperty(value = "可用")
    private String memFrees;
    @ApiModelProperty(value = "内存使用率")
    private String memoryUsage;
}
