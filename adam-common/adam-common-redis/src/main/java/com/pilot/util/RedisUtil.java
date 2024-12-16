package com.pilot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil工具类
 * 提供了一些常用的Redis操作，封装了RedisTemplate，简化了使用过程。
 */
@Component
@Slf4j
public class RedisUtil {

    // 缓存key的分隔符
    private static final String CACHE_KEY_SEPARATOR = ".";

    // 注入RedisTemplate，用于执行具体的Redis操作
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 构建缓存key
     * 将多个字符串参数连接成一个以"."为分隔符的字符串作为缓存的key。
     * 
     * @param parts 要连接的字符串数组
     * @return 拼接后的缓存key
     */
    public String buildKey(String... parts) {
        return String.join(CACHE_KEY_SEPARATOR, parts);
    }

    /**
     * 检查Redis中是否存在指定的key
     * 
     * @param key Redis中的key
     * @return 如果key存在，返回true；否则返回false
     */
    public boolean exists(String key) {
        // 判断Redis中是否存在指定的key
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除指定的key
     *
     * @param key Redis中的key
     */
    public void delete(String key) {
        // 删除指定的key
        redisTemplate.delete(key);
    }

    /**
     * 设置一个值到Redis中
     * 
     * @param key Redis中的key
     * @param value 要存储的值
     */
    public void set(String key, Object value) {
        // 使用RedisTemplate将值存入Redis
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置一个值到Redis中，仅在key不存在的情况下
     * 
     * @param key Redis中的key
     * @param value 要存储的值
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return 如果设置成功返回true；如果key已存在则返回false
     */
    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit timeUnit) {
        // 仅在key不存在时设置值，并设置过期时间
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit));
    }

    /**
     * 获取指定key的值
     * 
     * @param key Redis中的key
     * @param <T> 返回的值的类型
     * @return key对应的值，可能为null
     */
    public <T> T get(String key) {
        // 从Redis获取对应key的值，并强制转换为指定类型
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 向有序集合中添加一个值
     * 
     * @param key Redis中的key
     * @param value 要添加的值
     * @param score 值的分数
     * @return 如果添加成功返回true，已存在返回false
     */
    public boolean addToZSet(String key, Object value, double score) {
        // 向有序集合中添加一个值，成功返回true
        return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(key, value, score));
    }

    /**
     * 获取有序集合的元素个数
     * 
     * @param key Redis中的key
     * @return 集合中元素的个数
     */
    public Long getZSetSize(String key) {
        // 获取有序集合的元素数量
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取有序集合中的一部分元素
     * 
     * @param key Redis中的key
     * @param start 起始位置
     * @param end 结束位置
     * @return 从start到end的元素集合
     */
    public Set<Object> getZSetRange(String key, long start, long end) {
        // 获取指定范围的有序集合元素
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 从有序集合中移除指定的值
     * 
     * @param key Redis中的key
     * @param values 要移除的值
     * @return 被移除元素的个数
     */
    public Long removeFromZSet(String key, Object... values) {
        // 从有序集合中移除指定的值
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 获取有序集合中某个值的分数
     * 
     * @param key Redis中的key
     * @param value 要查询分数的值
     * @return 该值的分数，若值不存在则返回null
     */
    public Double getZSetScore(String key, Object value) {
        // 获取有序集合中某个值的分数
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 根据分数范围获取有序集合中的元素
     * 
     * @param key Redis中的key
     * @param min 最小分数
     * @param max 最大分数
     * @return 分数范围内的元素集合
     */
    public Set<Object> getZSetRangeByScore(String key, double min, double max) {
        // 获取分数在[min, max]范围内的元素
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 增加有序集合中某个值的分数
     * 
     * @param key Redis中的key
     * @param value 要增加分数的值
     * @param delta 增加的分数
     * @return 更新后的分数
     */
    public Double incrementZSetScore(String key, Object value, double delta) {
        // 增加某个值的分数，返回更新后的分数
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 获取有序集合中某个值的排名
     * 
     * @param key Redis中的key
     * @param value 要查询排名的值
     * @return 该值的排名，若值不存在则返回null
     */
    public Long getZSetRank(String key, Object value) {
        // 获取某个值的排名，排名从0开始
        return redisTemplate.opsForZSet().rank(key, value);
    }
}
