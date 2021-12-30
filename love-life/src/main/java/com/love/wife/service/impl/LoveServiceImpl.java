package com.love.wife.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.wife.entity.User;
import com.love.wife.entity.Wife;
import com.love.wife.mapper.LoveMapper;
import com.love.wife.mapper.UserMapper;
import com.love.wife.service.LoveService;
import com.lujia.model.Outcome;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/10 10:54
 */

@Service
public class LoveServiceImpl implements LoveService {
    @Resource
    private LoveMapper loveMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Outcome<List<Wife>> findLoveWifeInfo() {

        List<Wife> wifeLists = loveMapper.selectList(new QueryWrapper<Wife>());
        return Outcome.success(wifeLists);
    }

    @Override
    public Outcome<List<User>> findUserInfo() {
        return Outcome.success(userMapper.selectList(new QueryWrapper<User>()));
    }
}
