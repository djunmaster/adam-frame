package com.pilot.service;

import com.pilot.entity.param.BasePageParam;
import com.pilot.entity.param.BaseParam;
import com.pilot.entity.param.user.UserDeleteParam;
import com.pilot.entity.param.user.UserParam;
import com.pilot.entity.vo.UserVo;

import java.util.List;

public interface UserService {
    void addUser(UserParam userParam);

    void deleteUser(UserDeleteParam userDeleteParam);

    List<UserVo> userPage(BasePageParam basePageParam);
}