package com.love.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.sys.entity.User;
import com.love.sys.mapper.UserMapper;
import com.love.sys.service.UserService;
import com.lujia.model.Outcome;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 19:48
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> findUsers() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Outcome saveUser(User user) {
        int insert = userMapper.insert(user);

        return Outcome.status(insert);
    }
}
