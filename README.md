# Adam Frame - 企业级Spring Boot微服务脚手架

## 项目概述

Adam Frame 是一个基于 Spring Boot 2.5.3 的企业级微服务脚手架项目，采用模块化设计，提供了完整的Web开发基础设施，包括数据访问、缓存、日志、工具类等通用组件。

## 技术栈

### 核心框架
- **Spring Boot**: 2.5.3
- **Java**: 1.8
- **Maven**: 项目构建工具

### 数据库相关
- **MySQL**: 8.0.33 (数据库)
- **MyBatis-Plus**: 3.5.3 (ORM框架)
- **Druid**: 1.2.12 (数据库连接池)

### 缓存
- **Redis**: Spring Boot Data Redis
- **Commons Pool2**: 2.11.1 (Redis连接池)

### 日志
- **Log4j2**: 异步日志框架
- **Disruptor**: 3.4.4 (高性能队列)

### 工具库
- **Lombok**: 1.18.24 (代码简化)
- **Hutool**: 5.8.20 (Java工具类库)
- **MapStruct**: 1.5.3.Final (对象映射)
- **Jackson**: 2.11.4 (JSON处理)

### API文档
- **Swagger2**: 2.7.0 (API文档生成)
- **Knife4j**: 2.0.9 (Swagger增强)

### 其他
- **FreeMarker**: 2.3.30 (模板引擎)
- **Pinyin4j**: 2.5.0 (中文转拼音)

## 项目结构

```
adam-frame/
├── adam-dependencies/          # 依赖版本统一管理模块
├── adam-common/               # 通用组件聚合模块
│   ├── adam-common-web/       # Web通用组件
│   ├── adam-common-data/      # 数据访问通用组件
│   ├── adam-common-log/       # 日志通用组件
│   ├── adam-common-redis/     # Redis缓存通用组件
│   ├── adam-common-tool/      # 工具类组件
│   └── adam-common-starter/   # 自动配置启动器
├── adam-manage-api/           # 管理模块API接口定义
├── adam-manage/              # 管理模块实现
└── backup-poms/              # POM文件备份
```

## 模块详细说明

### 1. adam-dependencies
- **作用**: 统一管理所有依赖的版本，采用BOM(Bill of Materials)模式
- **特点**: 集中式版本管理，避免版本冲突

### 2. adam-common-web
- **功能**: Web层通用组件
- **包含**: 
  - Spring Boot Web Starter
  - AOP支持
  - 数据校验
  - Jackson JSON处理
  - 通用响应结果封装

### 3. adam-common-data
- **功能**: 数据访问层通用组件
- **包含**:
  - MySQL数据库连接
  - MyBatis-Plus ORM
  - Druid连接池
  - SQL美化拦截器
  - 自动填充处理器

### 4. adam-common-redis
- **功能**: Redis缓存通用组件
- **包含**:
  - Redis配置
  - 缓存工具类
  - 抽象缓存基类
  - 缓存初始化机制

### 5. adam-common-log
- **功能**: 日志通用组件
- **特点**: 
  - Log4j2异步日志
  - 高性能日志输出
  - 灵活的日志配置

### 6. adam-common-tool
- **功能**: 工具类组件
- **包含**:
  - Spring上下文工具类
  - Word文档导出服务
  - Bean转换工具
  - 通用工具方法

### 7. adam-manage-api
- **功能**: 管理模块API接口定义
- **包含**:
  - 用户管理API
  - 请求参数定义
  - 响应结果定义
  - Swagger注解

### 8. adam-manage
- **功能**: 管理模块具体实现
- **包含**:
  - 用户管理功能
  - Redis测试功能
  - 日志测试功能
  - Word导出功能

## 核心功能

### 用户管理
- 用户添加
- 用户删除
- 用户分页查询
- 用户信息导出(Word)

### 缓存管理
- Redis缓存配置
- 缓存工具类
- 缓存初始化
- 缓存抽象基类

### 数据访问
- MyBatis-Plus集成
- 自动填充字段
- SQL美化输出
- 分页查询支持

### 日志系统
- 异步日志输出
- 高性能队列
- 灵活配置

## 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/adamdb
    username: root
    password: 12345
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
```

### Redis配置
```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    lettuce:
      pool:
        max-active: 8
        max-wait: -1ms
```

### MyBatis-Plus配置
```yaml
mybatis-plus:
  mapper-locations: classpath:/com/pilot/mapper/*Mapper.xml
  type-aliases-package: com.adam.frame.model
  configuration:
    map-underscore-to-camel-case: true
```

## 快速开始

### 1. 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 3.0+

### 2. 数据库初始化
创建数据库 `adamdb` 并执行相关SQL脚本

### 3. 启动应用
```bash
mvn clean install
cd adam-manage
mvn spring-boot:run
```

### 4. 访问地址
- 应用地址: http://localhost:8080
- Druid监控: http://localhost:8080/druid (admin/123456)
- Swagger文档: http://localhost:8080/swagger-ui.html

## 设计特点

### 1. 模块化设计
- 清晰的模块划分
- 职责单一原则
- 高内聚低耦合

### 2. 统一依赖管理
- BOM模式管理版本
- 避免版本冲突
- 便于升级维护

### 3. 通用组件抽象
- 可复用的通用组件
- 标准化的开发模式
- 提高开发效率

### 4. 企业级特性
- 完善的日志系统
- 数据库连接池监控
- 缓存抽象设计
- API文档自动生成

## 开发规范

### 1. 包命名规范
- 基础包名: `com.pilot`
- 控制器: `com.pilot.controller`
- 服务层: `com.pilot.service`
- 数据访问: `com.pilot.mapper`

### 2. 代码规范
- 使用Lombok简化代码
- 统一异常处理
- 参数校验
- 日志记录

## 扩展指南

### 1. 添加新模块
1. 在根pom.xml中添加module
2. 创建模块目录和pom.xml
3. 继承adam-dependencies版本管理

### 2. 添加新的通用组件
1. 在adam-common下创建新模块
2. 定义组件接口和实现
3. 提供自动配置类

## 作者信息
- **项目名称**: adam-frame
- **作者**: djunmaster
- **GitHub**: https://github.com/djunmaster
- **版本**: 1.0-SNAPSHOT

## 许可证
本项目采用开源许可证，具体请查看LICENSE文件。
