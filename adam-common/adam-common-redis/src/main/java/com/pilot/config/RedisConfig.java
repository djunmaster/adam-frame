package com.pilot.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class RedisConfig {
    // 日志记录器，用于记录配置过程中的信息和错误
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    /**
     * Redis配置核心方法 - 创建RedisTemplate实例
     * 主要作用：配置Redis数据序列化和连接方式
     * <p>
     * 配置步骤：
     * 1. 创建RedisTemplate对象
     * 2. 配置key的序列化方式（使用字符串序列化）
     * 3. 配置value的序列化方式（使用JSON序列化）
     * 4. 设置Redis连接工厂
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // =================== 创建RedisTemplate对象 ===================
        // 用于Redis数据交互的核心模板类
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // =================== 配置KEY的序列化器 ===================
        // 使用StringRedisSerializer处理键的序列化和反序列化
        // 确保Redis中的key是可读的字符串格式
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        // 使用 StringRedisSerializer 来处理 Redis 中键的序列化和反序列化，存储到 Redis 中的键就会以字符串形式进行存储和读取。
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        // =================== 配置VALUE的序列化 ===================
        // 使用JSON序列化方式，可以将Java对象转换为JSON存储
        // 使用 Jackson2JsonRedisSerializer，这个序列化器将 Java 对象转换为 JSON 格式。这样，存储到 Redis 中的值就会是 JSON 格式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        // =================== 设置连接工厂 ===================
        // 配置Redis连接，用于建立与Redis服务器的连接
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    /**
     * JSON序列化配置方法
     * <p>
     * 主要功能：
     * 1. 创建Jackson序列化器
     * 2. 配置ObjectMapper的特殊属性
     * 3. 处理可能的配置异常
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        // =================== 创建JSON序列化器 ===================
        // 使用Jackson将Java对象转换为JSON格式
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // =================== 创建ObjectMapper ===================
        //  ObjectMapper负责 Java 对象和 JSON 数据之间进行转换。它负责将 Java 对象序列化为 JSON 格式（即将对象转换为字符串）以及将 JSON 字符串反序列化为 Java 对象。
        // ObjectMapper 还可以进行很多定制，例如格式化日期、指定序列化策略（比如排除某些字段），甚至可以与其他库集成
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // ===================  配置序列化和反序列化的规则 ===================
            // 允许序列化所有字段，包括私有字段
            objectMapper.setVisibility(
                    PropertyAccessor.ALL,
                    JsonAutoDetect.Visibility.ANY
            );

            // 在反序列化时，如果 JSON 数据包含 Java 类中没有的字段，不会报错，而是会被忽略。
            objectMapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false
            );

            // 将配置应用到JSON序列化器
            jsonRedisSerializer.setObjectMapper(objectMapper);

        } catch (Exception e) {
            // =================== 异常处理 ===================
            // 记录配置错误，并抛出运行时异常
            log.error("JSON序列化配置失败", e);
            throw new RuntimeException("JSON序列化配置异常", e);
        }

        return jsonRedisSerializer;
    }
}
