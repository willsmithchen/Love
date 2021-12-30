package com.love.wife.service;

import com.love.wife.entity.User;
import com.love.wife.entity.Wife;
import com.lujia.model.Outcome;

import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/8 0:27
 */

public interface LoveService {
    /**
     * 查找信息
     *
     * @return
     */
    Outcome<List<Wife>> findLoveWifeInfo();

    /**
     * 查询用户信息
     *
     * @return
     */
    Outcome<List<User>> findUserInfo();
}
