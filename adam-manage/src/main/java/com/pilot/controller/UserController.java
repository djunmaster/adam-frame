package com.pilot.controller;

import com.pilot.api.UserApi;
import com.pilot.entity.param.BasePageParam;
import com.pilot.entity.param.user.UserDeleteParam;
import com.pilot.entity.param.user.UserParam;
import com.pilot.entity.response.PageView;
import com.pilot.entity.response.ResponsePageResult;
import com.pilot.entity.response.ResponseResult;
import com.pilot.entity.vo.UserVo;
import com.pilot.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
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
    public ResponseResult<Void> addUser(UserParam userParam) {
        userService.addUser(userParam);
        return ResponseResult.ok();
    }

    /**
     * 删除用户
     *
     * @param userDeleteParam 用户删除参数
     * @return 删除结果，包含操作结果和删除的用户数量
     */
    @Override
    public ResponseResult<Void> deleteUser(UserDeleteParam userDeleteParam) {
        userService.deleteUser(userDeleteParam);
        return ResponseResult.ok();
    }

    /**
     * 获取用户分页信息
     *
     * @return 包含用户分页信息的响应结果
     */
    @Override
    public ResponsePageResult<UserVo> userPage(BasePageParam basePageParam) {
        List<UserVo> userVos = userService.userPage(basePageParam);
        PageView<UserVo> userVoPageView = new PageView<>();
        userVoPageView.setLists(userVos);
        return ResponsePageResult.ok(userVoPageView);
    }
}