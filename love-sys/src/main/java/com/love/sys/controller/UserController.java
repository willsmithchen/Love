package com.love.sys.controller;

import com.love.sys.entity.User;
import com.love.sys.service.UserService;
import com.lujia.model.Outcome;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 19:46
 * @description 用户管理
 */
@RestController
@RequestMapping(value = "/user-manager")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 查找所有的用戶信息
     *
     * @return 用戶列表
     */
    @GetMapping(value = "/findUsers")
    public Outcome<List<User>> findUsers() {
        List<User> userList = userService.findUsers();
        return Outcome.success(userList);
    }

    /**
     * 保存用戶信息
     *
     * @param user 用戶信息
     * @return 是否保存成功
     */
    @PostMapping(value = "/save-user")
    public Outcome saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
