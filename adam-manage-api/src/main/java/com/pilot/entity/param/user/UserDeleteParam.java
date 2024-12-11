package com.pilot.entity.param.user;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@Data
public class UserDeleteParam {
    // 删除用户id列表
    private List<Integer> userIds;
}
