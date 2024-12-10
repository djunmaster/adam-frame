package com.pilot.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
    MyMetaObjectHandler实现了MetaObjectHandler接口，这是一个由MyBatis-Plus提供的接口，允许开发者定义在执行CRUD操作之前对实体对象进行预处理的行为。
    这个类主要是mybatis-plus在数据库操作时自动填充特定字段，减少重复代码，提高开发效率和数据一致性

    MetaObjectHandler接口来为MyBatis-Plus配置一个自定义的字段自动填充处理器。
    这个类的主要作用是在执行插入或更新操作时，自动地为某些特定字段设置默认值或根据业务逻辑计算得出的新值。
*/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject, "createUser", String.class, "adam");
        this.strictInsertFill(metaObject, "updateUser", String.class, "adam");
        String nowTime = LocalDateTime.now().toString();
        this.strictInsertFill(metaObject, "createTime", String.class, nowTime);
        this.strictInsertFill(metaObject, "updateTime", String.class, nowTime);
        this.strictInsertFill(metaObject, "flag", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateUser", String.class, "adam");
        this.strictUpdateFill(metaObject, "updateTime", String.class, LocalDateTime.now().toString());
    }
}
