package com.love.wife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2020/8/19 1:32
 * 用户
 */

@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "用户实体类")
@TableName(value = "tb_user")
public class User {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID", position = 1)
    private Integer id;
    /**
     * 用户名称
     */
    @TableField(value = "userName")
    @ApiModelProperty(value = "用户名称", position = 2)
    private String userName;
    /**
     * 用户密码
     */
    @TableField(value = "passWord")
    @ApiModelProperty(value = "用户密码", position = 3)
    private String passWord;

}
