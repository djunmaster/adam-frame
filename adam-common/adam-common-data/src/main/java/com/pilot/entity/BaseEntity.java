package com.pilot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    // 定义序列化ID
    private static final long serialVersionUID = 1L;

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
