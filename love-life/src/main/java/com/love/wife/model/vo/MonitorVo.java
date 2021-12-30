package com.love.wife.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/28 22:04
 * 监控vo
 */
@Data
@ApiModel(value = "监控参数")
public class MonitorVo {
    @ApiModelProperty(value = "cpu")
    private CpuVo cpu;
    @ApiModelProperty(value = "内存")
    private MemoryVo memory;
    @ApiModelProperty(value = "硬盘")
    private DiskVo disk;
    @ApiModelProperty(value = "网络流量")
    private List<NetVo> netVoList;

}
