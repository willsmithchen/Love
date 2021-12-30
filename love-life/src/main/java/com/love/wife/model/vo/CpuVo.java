package com.love.wife.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/28 22:05
 * cpu出参参数
 */

@Data
@ApiModel(value = "CPU出参")
public class CpuVo {
    @ApiModelProperty(value = "CPU用户使用率")
    private String user;
    @ApiModelProperty(value = "CPU系统使用率")
    private String sys;
    @ApiModelProperty(value = "CPU当前等待率")
    private String wait;
    @ApiModelProperty(value = "CPU当前错误率")
    private String nice;
    @ApiModelProperty(value = "CPU当前空闲率")
    private String idle;
    @ApiModelProperty(value = "CPU总的使用率")
    private String combined;
}
