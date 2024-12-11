package com.pilot.api;

import com.pilot.entity.param.BasePageParam;
import com.pilot.entity.param.BaseParam;
import com.pilot.entity.param.user.UserDeleteParam;
import com.pilot.entity.param.user.UserParam;
import com.pilot.entity.response.ResponseResult;
import com.pilot.entity.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "UserApi", tags = "用户接口")
@RequestMapping("/user")
public interface UserApi {

    @ApiOperation(value = "[1] 添加用户", notes = "添加用户信息")
    @RequestMapping(value = "/addUser", method = {RequestMethod.POST})
    public ResponseResult<Void> addUser(@RequestBody @Validated UserParam userParam);

    @ApiOperation(value = "[2] 删除用户", notes = "删除用户")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResponseResult<Void> deleteUser(@RequestBody UserDeleteParam userDeleteParam);


    @ApiOperation(value = "[3] 分页查询用户列表", notes = "分页查询用户列表")
    @RequestMapping(value = "/page", method = {RequestMethod.POST})
    public ResponseResult<UserVo> userPage(@RequestBody BasePageParam basePageParam);
}