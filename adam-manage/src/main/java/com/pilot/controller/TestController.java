package com.pilot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/testLog", method = RequestMethod.GET)
    public void testLog() {
        long startCurTime = System.currentTimeMillis();
        IntStream.range(0, 10000).forEach(i -> {
            log.info("遍历回合：{}", i + 1);
        });
        long endTime = System.currentTimeMillis();
        log.info("执行花费时间：" + (endTime - startCurTime));
    }
}