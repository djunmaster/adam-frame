package com.pilot.entity.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@TableName(value = "user")
@Data
public class User {
    @TableId(value = "userId", type = IdType.AUTO)
    private Long userId;

    private String name;

    private Integer age;


    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer flag;

    /*用于乐观锁判断*/
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
}