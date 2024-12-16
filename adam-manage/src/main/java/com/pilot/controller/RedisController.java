package com.pilot.controller;

import com.pilot.util.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/set")
    public String set() {
        redisUtil.set("testValue", "adam-frame 项目脚手架111");
        return "success";
    }

    @RequestMapping("/get")
    public String get() {
        return redisUtil.get("testValue");
    }
}
