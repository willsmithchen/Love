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
 * @date 2020/8/19 1:37
 * 角色
 */


@Data
@ToString
@Accessors(chain = true)
@TableName(value = "tb_role")
@ApiModel(value = "角色实体类")
public class Role {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id", position = 1)
    private Long id;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", position = 2)
    private String userName;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "昵称", position = 3)
    private String name;
}
