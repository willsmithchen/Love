package com.love.wife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/8 0:26
 */

@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "妻子实体类参数")
@TableName(value = "my_love_wife")
public class Wife {
    @ApiModelProperty(value = "主键id", position = 1)
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @ApiModelProperty(value = "姓名", position = 2)
    private String name;
    @ApiModelProperty(value = "内容", position = 3)
    private String context;
    @ApiModelProperty(value = "年龄", position = 4)
    private Integer age;
    @ApiModelProperty(value = "性别", position = 5)
    private String sex;
    @ApiModelProperty(value = "爱好", position = 6)
    private String hobby;
    @ApiModelProperty(value = "照片", position = 7)
    private String img;
    @ApiModelProperty(value = "备注", position = 8)
    private String remark;
}
