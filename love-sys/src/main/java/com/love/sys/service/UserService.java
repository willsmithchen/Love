package com.love.sys.service;

import com.love.sys.entity.User;
import com.lujia.model.Outcome;

import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 19:47
 */

public interface UserService {
    /**
     * 查找所有用户信息
     *
     * @return 用户列表
     */
    List<User> findUsers();

    /**
     * 保存用戶信息
     *
     * @param user -用戶參數
     * @return
     */
    Outcome saveUser(User user);
}
