package com.pilot.cache;

import org.springframework.stereotype.Component;

/**
 * 缓存操作的抽象类，提供缓存的基本操作接口和默认实现。
 * 子类需要实现具体的缓存初始化、获取和清除逻辑。
 */
@Component
public abstract class AbstractCache {

    /**
     * 初始化缓存。
     * 子类需要实现具体的缓存初始化逻辑，例如加载数据到缓存中。
     */
    public abstract void initCache();

    /**
     * 获取缓存数据。
     * 子类需要根据具体业务逻辑实现数据的获取。
     *
     * @param <T> 返回的数据类型
     * @return 缓存中的数据
     */
    public abstract <T> T getCache();

    /**
     * 清除缓存。
     * 子类需要实现具体的缓存清除逻辑，例如释放内存或删除缓存数据。
     */
    public abstract void clearCache();

    /**
     * 重新加载缓存。
     * 默认实现为先清除缓存，然后重新初始化缓存。子类可以根据需要重写此方法。
     */
    public void reloadCache() {
        clearCache();
        initCache();
    }
}
