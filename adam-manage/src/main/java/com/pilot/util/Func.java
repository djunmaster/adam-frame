package com.pilot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.*;

public class Func {

    private static final Logger log = LoggerFactory.getLogger(Func.class);

    private Func() {
        // 工具类私有构造，防止实例化
    }

    // 如果表达式为 true，执行给定操作
    public static void ifTrue(boolean expression, Runnable func) {
        if (expression) func.run();
    }

    // 如果表达式为 true 返回 Supplier 提供的值，否则返回默认值
    public static <T> T ifTrue(boolean expression, Supplier<T> func, T defaultValue) {
        return expression ? func.get() : defaultValue;
    }

    // 如果表达式为 false，执行给定操作
    public static void ifFalse(boolean expression, Runnable func) {
        ifTrue(!expression, func);
    }

    // 如果表达式为 false 返回 Supplier 提供的值，否则返回默认值
    public static <T> T ifFalse(boolean expression, Supplier<T> func, T defaultValue) {
        return ifTrue(!expression, func, defaultValue);
    }

    // 根据条件执行 if 或 else 操作
    public static void ifTrueOrElse(boolean expression, Runnable func, Runnable elseFunc) {
        if (expression) {
            func.run();
        } else {
            elseFunc.run();
        }
    }

    // 如果对象为 null 执行操作
    public static void ifNull(Object obj, Runnable func) {
        if (obj == null) func.run();
    }

    // 如果对象为 null 返回 Supplier 提供的值
    public static <T> T ifNull(T obj, Supplier<T> func) {
        return ifNull(obj, func, obj);
    }

    // 如果对象为 null 返回 Supplier 提供的值，否则返回默认值
    public static <T> T ifNull(T obj, Supplier<T> func, T defaultValue) {
        return obj == null ? func.get() : defaultValue;
    }

    // 如果对象非 null 执行操作
    public static void ifNotNull(Object obj, Runnable func) {
        if (obj != null) func.run();
    }

    // 如果对象非 null 执行 Consumer 提供的逻辑
    public static <T> void ifNotNull(T obj, Consumer<T> func) {
        if (obj != null) func.accept(obj);
    }

    // 如果对象非 null 返回 Function 应用的结果，否则返回默认值
    public static <T, R> R ifNotNull(T obj, Function<T, R> func, R defaultValue) {
        return obj != null ? func.apply(obj) : defaultValue;
    }

    // 如果字符串为空执行操作
    public static void ifEmpty(String string, Runnable func) {
        if (StringUtil.isEmpty(string)) func.run();
    }

    // 如果字符串为空返回 Supplier 提供的值，否则返回默认值
    public static <T> T ifEmpty(String string, Supplier<T> func, T defaultValue) {
        return StringUtil.isEmpty(string) ? func.get() : defaultValue;
    }

    // 如果集合为空执行操作
    public static <T> void ifEmpty(Collection<T> collection, Runnable func) {
        if (CollectionUtil.isEmpty(collection)) func.run();
    }

    // 如果集合为空返回 Supplier 提供的值，否则返回默认值
    public static <T> T ifEmpty(Collection<T> collection, Supplier<T> func, T defaultValue) {
        return CollectionUtil.isEmpty(collection) ? func.get() : defaultValue;
    }

    // 如果字符串非空执行操作
    public static void ifNotEmpty(String string, Runnable func) {
        if (StringUtil.isNotEmpty(string)) func.run();
    }

    // 如果字符串非空执行 Consumer 提供的逻辑
    public static void ifNotEmpty(String string, Consumer<String> func) {
        if (StringUtil.isNotEmpty(string)) func.accept(string);
    }

    // 如果集合非空执行操作
    public static <T> void ifNotEmpty(Collection<T> collection, Runnable func) {
        if (CollectionUtil.isNotEmpty(collection)) func.run();
    }

    // 如果集合非空执行 Consumer 提供的逻辑
    public static <T> void ifNotEmpty(Collection<T> collection, Consumer<Collection<T>> func) {
        if (CollectionUtil.isNotEmpty(collection)) func.accept(collection);
    }

    // 如果映射非空执行 Consumer 提供的逻辑
    public static <K, V> void ifNotEmpty(Map<K, V> map, Consumer<Map<K, V>> func) {
        if (CollectionUtil.isNotEmpty(map)) func.accept(map);
    }

    // 返回二元操作符：保留第一个参数
    public static <T> BinaryOperator<T> getFirstFunction() {
        return (v1, v2) -> v1;
    }

    // 返回二元操作符：保留最后一个参数
    public static <T> BinaryOperator<T> getLastFunction() {
        return (v1, v2) -> v2;
    }
}
