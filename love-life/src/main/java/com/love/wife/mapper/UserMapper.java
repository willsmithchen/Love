package com.love.wife.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.wife.entity.Role;
import com.love.wife.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/19 2:03
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户详情
     *
     * @param userName -用户详情
     * @return
     */
    User findUserByUserName(String userName);

    /**
     * 根据用户名称查询用户角色
     *
     * @param roleName
     * @return
     */
    List<Role> findRoleByRoleName(String roleName);
}
