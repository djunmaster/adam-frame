package com.pilot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pilot.entity.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}