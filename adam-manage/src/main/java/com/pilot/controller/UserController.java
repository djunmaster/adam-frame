package com.pilot.controller;

import com.pilot.api.UserApi;
import com.pilot.entity.param.UserParam;
import com.pilot.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController implements UserApi {

    @Resource
    private UserService userService;

    /**
     * 添加用户
     *
     * @param userParam 用户参数对象，包含用户的各种信息
     * @return 添加的用户ID，如果添加失败则返回null
     */
    @Override
    public Integer addUser(UserParam userParam) {
        return userService.addUser(userParam);
    }
}