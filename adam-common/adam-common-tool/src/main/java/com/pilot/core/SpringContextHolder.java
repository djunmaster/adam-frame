package com.pilot.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Spring 上下文持有工具类，用于在普通类中获取 Spring 管理的 Bean。
 *
 * <h3>核心优势：</h3>
 * <ul>
 * <li><b>健壮性：</b> 对上下文进行空检查，防止在容器未初始化完成时调用导致空指针。</li>
 * <li><b>内存安全：</b> 实现 {@link DisposableBean} 接口，在容器销毁时自动清理静态持有的上下文，防止内存泄漏。</li>
 * <li><b>线程安全：</b> 使用 {@code volatile} 关键字确保 {@code ApplicationContext} 在多线程环境下的可见性。</li>
 * <li><b>Web 环境安全：</b> 在获取 Request/Response 前进行检查，防止在非 Web 环境下调用出错。</li>
 * </ul>
 *
 * <h3>⚠️ 注意：</h3>
 * <p>
 * 虽然该工具类提供了便利，但过度使用它会破坏 Spring 的依赖注入（DI）和控制反转（IoC）核心思想，
 * 导致代码耦合度增高，不易进行单元测试。
 * </p>
 * <p>
 * <b>最佳实践是优先使用构造函数注入、字段注入等标准依赖注入方式。</b>
 * 仅在无法通过常规 DI 获取 Bean 的特殊场景（如静态方法、某些遗留代码或框架集成）下，才应考虑使用此类。
 * </p>
 *
 * @author liuyd
 */
@Component
public final class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    /**
     * 使用 volatile 确保多线程环境下的可见性。
     */
    private static volatile ApplicationContext applicationContext;

    /**
     * 获取 Spring 应用上下文。
     *
     * @return {@link ApplicationContext}
     * @throws IllegalStateException 如果 applicationContext 未被注入。
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * Spring 容器启动时，自动注入 ApplicationContext。
     *
     * @param applicationContext 应用上下文
     * @throws BeansException 如果注入失败
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 根据 Bean 名称获取 Bean。
     *
     * @param name Bean 的名称
     * @return 对应名称的 Bean 实例
     * @throws BeansException 如果找不到 Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据 Bean 类型获取 Bean。
     *
     * @param requiredType Bean 的 Class 类型
     * @return 对应类型的 Bean 实例
     * @throws BeansException 如果找不到或存在多个该类型的 Bean
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据 Bean 名称和类型获取 Bean。
     *
     * @param name         Bean 的名称
     * @param requiredType Bean 的 Class 类型
     * @return 对应名称和类型的 Bean 实例
     * @throws BeansException 如果找不到 Bean
     */
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        assertContextInjected();
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 获取指定类型的所有 Bean。
     *
     * @param clazz Bean 的 Class 类型
     * @return 一个包含所有匹配 Bean 的 Map，key 为 bean name，value 为 bean instance
     * @throws BeansException 如果发生错误
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) throws BeansException {
        assertContextInjected();
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 检查容器中是否存在指定名称的 Bean。
     *
     * @param name Bean 的名称
     * @return 如果存在则返回 true，否则返回 false
     */
    public static boolean containsBean(String name) {
        assertContextInjected();
        return applicationContext.containsBean(name);
    }

    /**
     * 获取当前 HTTP 请求的 {@link HttpServletRequest} 对象。
     * <p><b>注意:</b> 此方法只能在 Web 环境下的请求处理线程中调用。</p>
     *
     * @return 当前的 HttpServletRequest
     * @throws IllegalStateException 如果当前线程没有绑定 Request 上下文
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("无法获取 HttpServletRequest，因为当前线程不是一个 Web 请求线程。");
        }
        return attributes.getRequest();
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    /**
     * 获取当前 HTTP 请求的 {@link HttpServletResponse} 对象。
     * <p><b>注意:</b> 此方法只能在 Web 环境下的请求处理线程中调用。</p>
     *
     * @return 当前的 HttpServletResponse
     * @throws IllegalStateException 如果当前线程没有绑定 Request 上下文
     */
    public static HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("无法获取 HttpServletResponse，因为当前线程不是一个 Web 请求线程。");
        }
        return attributes.getResponse();
    }

    /**
     * 获取当前 HTTP 请求的 {@link HttpSession} 对象。
     *
     * @return 当前的 HttpSession
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    /**
     * Spring 容器关闭时，清理静态持有的 ApplicationContext。
     */
    @Override
    public void destroy() {
        clearHolder();
    }

    /**
     * 清理静态持有的 ApplicationContext。
     */
    public static void clearHolder() {
        // LOG.debug("清除SpringContextHolder中的ApplicationContext: {}", applicationContext);
        applicationContext = null;
    }

    /**
     * 检查 ApplicationContext 是否已被注入。
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "ApplicationContext 未被注入。请确保 SpringContextHolder 已被 Spring 容器扫描并初始化。" +
                            "检查点：1. 是否在 Spring Boot 主类同级或子包下。 2. 是否添加了 @Component 注解。"
            );
        }
    }
}
