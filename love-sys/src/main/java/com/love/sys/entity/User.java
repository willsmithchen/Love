package com.love.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 19:21
 * <p>
 * @description 用户实体类
 */
@Data
@TableName(value = "sys_user")
public class User {
    /**
     * 用户id
     */
    @TableField(value = "userId")
    private Long userId;
    /**
     * 部门id
     */
    @TableField(value = "deptId")
    private Long deptId;
    /**
     * 用户账号
     */
    @TableField(value = "userName")
    private String userName;
    /**
     * 用户昵称
     */
    @TableField(value = "nickName")
    private String nickName;
    /**
     * 用户类型
     */
    @TableField(value = "userType")
    private String userType;
    /**
     * 用户邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 用户性别
     */
    @TableField(value = "sex")
    private String sex;
    /**
     * 头像地址
     */
    @TableField(value = "avatar")
    private String avatar;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 账号状态（0正常，1未知）
     */
    @TableField(value = "status")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField(value = "delFlag")
    private String delFlag;
    /**
     * 最后登录ip
     */
    @TableField(value = "loginIp")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @TableField(value = "loginDate")
    private String loginDate;
    /**
     * 创建者
     */
    @TableField(value = "createBy")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private String createTime;
    /**
     * 更新者
     */
    @TableField(value = "updateBy")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private String updateTime;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 企业id
     */
    @TableField(value = "clientId")
    private String clientId;
    /**
     * 用户类型(1001,云平台 admin 1002,云平台普通用户 1003,企业admin 1004,企业其他账号)
     */
    @TableField(value = "userClass")
    private String userClass;
}
