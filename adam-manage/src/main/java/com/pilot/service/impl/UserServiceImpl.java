package com.pilot.service.impl;

import com.pilot.entity.model.User;
import com.pilot.entity.param.UserParam;
import com.pilot.mapper.UserMapper;
import com.pilot.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int addUser(UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        return userMapper.insert(user);
    }
}