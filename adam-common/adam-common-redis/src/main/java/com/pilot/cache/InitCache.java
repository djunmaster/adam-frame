package com.pilot.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.pilot.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 缓存初始化类。
 * 在 Spring Boot 应用启动时执行，加载所有继承自 AbstractCache 的缓存实例并初始化它们。
 */
@Component
@Slf4j
public class InitCache implements CommandLineRunner {

    /**
     * 在应用启动时运行的初始化方法。
     * 遍历 Spring 容器中的所有 AbstractCache 实例，并调用其初始化方法。
     *
     * @param args 应用启动时传递的参数
     * @throws Exception 如果初始化过程中发生异常
     */
    @Override
    public void run(String... args) throws Exception {
        // 获取 Spring 应用上下文
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();

        // 获取所有继承自 AbstractCache 的 Bean 实例
        Map<String, AbstractCache> abstractCacheBeanMap = applicationContext.getBeansOfType(AbstractCache.class);

        // 如果没有找到任何 AbstractCache 实例，则直接返回
        if (CollectionUtil.isEmpty(abstractCacheBeanMap)) {
            log.warn("未找到任何 AbstractCache 的实现类，无需初始化缓存。");
            return;
        }

        // 遍历所有 AbstractCache 实例并初始化缓存
        for (Map.Entry<String, AbstractCache> entry : abstractCacheBeanMap.entrySet()) {
            try {
                AbstractCache abstractCache = entry.getValue();
                abstractCache.initCache();
                log.info("缓存 [{}] 初始化成功", entry.getKey());
            } catch (Exception e) {
                log.error("缓存 [{}] 初始化失败：{}", entry.getKey(), e.getMessage(), e);
            }
        }
        log.info("所有缓存初始化完成，初始化类：{}", this.getClass().getSimpleName());
    }
}
