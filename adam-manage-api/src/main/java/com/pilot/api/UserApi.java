package com.pilot.api;

import com.pilot.entity.param.UserParam;
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
    public Integer addUser(@RequestBody @Validated UserParam userParam);

}