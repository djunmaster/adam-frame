package com.pilot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pilot.entity.model.User;
import com.pilot.entity.param.BasePageParam;
import com.pilot.entity.param.user.UserDeleteParam;
import com.pilot.entity.param.user.UserParam;
import com.pilot.entity.vo.UserVo;
import com.pilot.mapper.UserMapper;
import com.pilot.service.UserService;
import com.pilot.util.BeanConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void addUser(UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        userMapper.insert(user);
    }

    @Override
    public void deleteUser(UserDeleteParam userDeleteParam) {
        userMapper.deleteByIds(userDeleteParam.getUserIds());
    }

    @Override
    public List<UserVo> userPage(BasePageParam basePageParam) {
        if (ObjectUtil.isNull(basePageParam)) {
            return Collections.emptyList();
        }
        Page<User> userPage = new Page<>(basePageParam.getPage(), basePageParam.getSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getFlag, 0);
        Page<User> userPages = userMapper.selectPage(userPage, wrapper);
        List<User> records = userPages.getRecords();
        return BeanConvertUtil.convertListTo(records, UserVo::new);
    }
}