package com.pilot.entity.param.user;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class UserParam {

    @Size(min = 3, max = 30, message = "用户名长度在3到30之间")
    private String name;

    @Min(value = 1, message = "年龄不能小于1岁")
    @Max(value = 100, message = "年龄不能大于100岁")
    private Integer age;
}