package com.pilot.entity.model;

import com.baomidou.mybatisplus.annotation.*;
import com.pilot.entity.dto.ObjectEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
@Data
public class User extends ObjectEntity {
    @TableId(value = "userId", type = IdType.AUTO)
    private Long userId;

    private String name;

    private Integer age;
}