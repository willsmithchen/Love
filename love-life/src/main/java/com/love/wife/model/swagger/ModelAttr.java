package com.love.wife.model.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回属性
 *
 * @author kevin
 */
@Data
@ApiModel(value = "返回属性")
public class ModelAttr implements Serializable {

    private static final long serialVersionUID = -4074067438450613643L;

    /**
     * 类名
     */
    @ApiModelProperty(value = "类名", position = 1)
    private String className = StringUtils.EMPTY;
    /**
     * 属性名
     */
    @ApiModelProperty(value = "属性名", position = 2)
    private String name = StringUtils.EMPTY;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", position = 3)
    private String type = StringUtils.EMPTY;
    /**
     * 是否必填
     */
    @ApiModelProperty(value = "是否必填", position = 4)
    private Boolean require = false;
    /**
     * 属性描述
     */
    @ApiModelProperty(value = "属性描述", position = 5)
    private String description;
    /**
     * 嵌套属性列表
     */
    @ApiModelProperty(value = "嵌套属性列表", position = 6)
    private List<ModelAttr> properties = new ArrayList<>();

    /**
     * 是否加载完成，避免循环引用
     */
    @ApiModelProperty(value = "是否加载完成，避免循环引用", position = 7)
    private boolean isCompleted = false;
}
