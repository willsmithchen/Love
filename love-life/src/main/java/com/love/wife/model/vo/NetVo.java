package com.love.wife.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/10/28 22:15
 * 流量出参
 */

@Data
@ApiModel(value = "流量出参")
public class NetVo {
    @ApiModelProperty(value = "当前时间")
    private Date currentTime;
    @ApiModelProperty(value = "当前接收包裹数")
    private Long currentRxPackets;
    @ApiModelProperty(value = "当前发送包裹数")
    private Long currentTxPackets;
    @ApiModelProperty(value = "当前接收字节数")
    private Long currentRxBytes;
    @ApiModelProperty(value = "当前发送字节数")
    private Long currentTxBytes;
    @ApiModelProperty(value = "接收总包裹数")
    private Long rxPackets;
    @ApiModelProperty(value = "发送总包裹数")
    private Long txPackets;
    @ApiModelProperty(value = "接收总字节数")
    private Long rxBytes;
    @ApiModelProperty(value = "发送总字节数")
    private Long txBytes;


}
