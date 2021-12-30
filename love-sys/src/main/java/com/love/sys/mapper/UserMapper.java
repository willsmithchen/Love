package com.love.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 19:45
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
