package com.pilot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.pilot.mapper")
public class AdamApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdamApplication.class);
    }
}
