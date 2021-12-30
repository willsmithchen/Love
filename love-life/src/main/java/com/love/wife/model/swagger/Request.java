package com.love.wife.model.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by XiuYin.Cui on 2018/1/11.
 */
@Data
@ApiModel(value = "请求参数")
public class Request implements Serializable {

    /**
     * 参数名
     */
    @ApiModelProperty(value = "参数名",position = 1)
    private String name;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型",position = 2)
    private String type;

    /**
     * 参数类型
     */
    @ApiModelProperty(value = "参数类型",position = 3)
    private String paramType;

    /**
     * 是否必填
     */
    @ApiModelProperty(value = "是否必填",position = 4)
    private Boolean require;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明",position = 5)
    private String remark;

    /**
     * 复杂对象引用
     */
    @ApiModelProperty(value = "复杂对象引用",position = 6)
    private ModelAttr modelAttr;
}
