package com.love.wife.model.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/1/11.
 */
@Data
@ApiModel(value = "表名称")
public class Table {

    /**
     * 大标题
     */
    @ApiModelProperty(value = "大标题",position = 1)
    private String title;
    /**
     * 小标题
     */
    @ApiModelProperty(value = "小标题",position = 2)
    private String tag;
    /**
     * url
     */
    @ApiModelProperty(value = "url",position = 3)
    private String url;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述",position = 4)
    private String description;

    /**
     * 请求参数格式
     */
    @ApiModelProperty(value = "请求参数格式",position = 5)
    private String requestForm;

    /**
     * 响应参数格式
     */
    @ApiModelProperty(value = "响应参数格式",position = 6)
    private String responseForm;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式",position = 7)
    private String requestType;

    /**
     * 请求体
     */
    @ApiModelProperty(value = "请求体",position = 8)
    private List<Request> requestList;

    /**
     * 返回体
     */
    @ApiModelProperty(value = "返回体",position = 9)
    private List<Response> responseList;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数",position = 10)
    private String requestParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数",position = 11)
    private String responseParam;

    /**
     * 返回属性列表
     */
    @ApiModelProperty(value = "返回属性列表",position = 12)
    private ModelAttr modelAttr = new ModelAttr();
}
