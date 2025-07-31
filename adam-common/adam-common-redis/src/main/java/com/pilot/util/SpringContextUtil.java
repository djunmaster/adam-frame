package com.pilot.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import org.springframework.lang.NonNull;

/**
 * Spring 上下文工具类，用于获取 Spring 容器中的 Bean 实例。
 * 当无法直接通过依赖注入的方式获取 Bean 时，可以使用此工具类。
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    /**
     * 设置 Spring 应用上下文。
     * 该方法由 Spring 框架在应用启动时自动调用。
     *
     * @param applicationContext Spring 提供的 ApplicationContext 实例
     * @throws BeansException 如果上下文设置失败
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 通过 Bean 的类型从 Spring 容器中获取 Bean 实例。
     *
     * @param <T>   要获取的 Bean 类型
     * @param clazz 要获取的 Bean 的 Class 对象
     * @return 指定类型的 Bean 实例
     * @throws BeansException 如果 Bean 获取失败
     */
    public static <T> T getBean(@NonNull Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过 Bean 的名称从 Spring 容器中获取 Bean 实例。
     *
     * @param beanName 要获取的 Bean 名称
     * @return Bean 实例
     * @throws BeansException 如果 Bean 获取失败
     */
    public static Object getBean(@NonNull String beanName) {
        return applicationContext.getBean(beanName);
    }
}

