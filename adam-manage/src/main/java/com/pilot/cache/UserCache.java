package com.pilot.cache;

import com.pilot.cache.AbstractCache;
import com.pilot.util.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户缓存实现类，用于管理用户相关的数据缓存。
 * 继承自 AbstractCache，提供具体的缓存初始化、获取和清除逻辑。
 */
@Component
public class UserCache extends AbstractCache {

    // 用户缓存的 Redis 键名
    private static final String USER_CACHE_KEY = "USER";

    @Resource
    private RedisUtil redisUtil;

    /**
     * 初始化用户缓存。
     * 将用户数据（如从数据库获取）写入 Redis 缓存。
     */
    @Override
    public void initCache() {
        // 示例：这里直接写入静态数据，实际应用中应从数据库或其他数据源加载用户数据。
        redisUtil.set(USER_CACHE_KEY, "adam1");
    }

    /**
     * 获取用户缓存数据。
     * 如果缓存存在且可用，返回缓存中的数据；如果缓存不存在或失效，则重新加载缓存后返回数据。
     *
     * @param <T> 返回的数据类型
     * @return 缓存中的用户数据
     */
    @Override
    public <T> T getCache() {
        // 判断缓存是否存在，若不存在则重新加载缓存
        if (!redisUtil.exists(USER_CACHE_KEY)) {
            reloadCache();
        }
        return (T) redisUtil.get(USER_CACHE_KEY);
    }

    /**
     * 清除用户缓存。
     * 删除 Redis 中对应的缓存键。
     */
    @Override
    public void clearCache() {
        redisUtil.delete(USER_CACHE_KEY);
    }
}
