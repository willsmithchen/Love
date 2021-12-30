package com.love.wife.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/28 22:13
 */

@Data
@ApiModel(value = "磁盘出参")
public class DiskVo {
    @ApiModelProperty(value = "总容量")
    private String total;
    @ApiModelProperty(value = "剩余大小")
    private String free;
    @ApiModelProperty(value = "可用大小")
    private String avail;
    @ApiModelProperty(value = "已使用大小")
    private String used;
    @ApiModelProperty(value = "资源利用率")
    private String usePercent;

}
