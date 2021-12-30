package com.love.wife.model.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by XiuYin.Cui on 2018/1/11.
 */
@Data
@ApiModel(value = "响应参数")
public class Response implements Serializable {

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数",position = 1)
    private String description;

    /**
     * 参数名
     */
    @ApiModelProperty(value = "参数名",position = 2)
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",position = 3)
    private String remark;
}
