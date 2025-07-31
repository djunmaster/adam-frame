package com.pilot.tools.functions;

import java.util.Collection;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import cn.hutool.core.collection.CollectionUtil;
import com.pilot.tools.lists.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Func {
    private static final Logger log = LoggerFactory.getLogger(Func.class);

    public Func() {
    }

    public static void ifTrue(boolean expression, Runnable func) {
        if (expression) {
            func.run();
        }

    }

    public static <T> T ifTrue(boolean expression, Supplier<T> func, T defaultValue) {
        return expression ? func.get() : defaultValue;
    }

    public static void ifFalse(boolean expression, Runnable func) {
        ifTrue(!expression, func);
    }

    public static <T> T ifFalse(boolean expression, Supplier<T> func, T defaultValue) {
        return ifTrue(!expression, func, defaultValue);
    }

    public static void ifTrueOrElse(boolean expression, Runnable func, Runnable elseFunc) {
        if (expression) {
            func.run();
        } else {
            elseFunc.run();
        }

    }

    public static void ifNull(Object obj, Runnable func) {
        if (obj == null) {
            func.run();
        }

    }

    public static <T> T ifNull(T obj, Supplier<T> func) {
        return ifNull(obj, func, obj);
    }

    public static <T> T ifNull(T obj, Supplier<T> func, T defaultValue) {
        return obj == null ? func.get() : defaultValue;
    }

    public static void ifNotNull(Object obj, Runnable func) {
        if (obj != null) {
            func.run();
        }
    }

    public static <T> void ifNotNull(T obj, Consumer<T> func) {
        if (obj != null) {
            func.accept(obj);
        }

    }

    public static <T, R> R ifNotNull(T obj, Function<T, R> func, R defaultValue) {
        return obj != null ? func.apply(obj) : defaultValue;
    }

    public static <T, R> R ifNotNull(T obj, Supplier<R> func) {
        return (R) ifNotNull(obj, (Supplier)func, (Object)null);
    }

    public static <T, R> R ifNotNull(T obj, Supplier<R> func, R defaultValue) {
        return obj != null ? func.get() : defaultValue;
    }

    public static void ifEmpty(String string, Runnable func) {
        if (StringUtil.isEmpty(string)) {
            func.run();
        }

    }

    public static <T> T ifEmpty(String string, Supplier<T> func, T defaultValue) {
        return StringUtil.isEmpty(string) ? func.get() : defaultValue;
    }

    public static <T> void ifEmpty(Collection<T> collection, Runnable func) {
        if (CollectionUtil.isEmpty(collection)) {
            func.run();
        }

    }

    public static <T> T ifEmpty(Collection<T> collection, Supplier<T> func, T defaultValue) {
        return CollectionUtil.isEmpty(collection) ? func.get() : defaultValue;
    }

    public static void ifNotEmpty(String string, Runnable func) {
        if (StringUtil.isNotEmpty(string)) {
            func.run();
        }

    }

    public static void ifNotEmpty(String string, Consumer<String> func) {
        if (StringUtil.isNotEmpty(string)) {
            func.accept(string);
        }

    }

    public static <R> R ifNotEmpty(String string, Function<String, R> func, R defaultValue) {
        return StringUtil.isNotEmpty(string) ? func.apply(string) : defaultValue;
    }

    public static <T> void ifNotEmpty(Collection<T> collection, Runnable func) {
        if (CollectionUtil.isNotEmpty(collection)) {
            func.run();
        }

    }

    public static <T> void ifNotEmpty(Collection<T> collection, Consumer<Collection<T>> func) {
        if (CollectionUtil.isNotEmpty(collection)) {
            func.accept(collection);
        }

    }

    public static <K, V> void ifNotEmpty(Map<K, V> map, Runnable func) {
        if (CollectionUtil.isNotEmpty(map)) {
            func.run();
        }

    }

    public static <K, V> void ifNotEmpty(Map<K, V> collection, Consumer<Map<K, V>> func) {
        if (CollectionUtil.isNotEmpty(collection)) {
            func.accept(collection);
        }

    }

    public static <T, R> R ifNotEmpty(Collection<T> collection, Function<Collection<T>, R> func, R defaultValue) {
        return CollectionUtil.isNotEmpty(collection) ? func.apply(collection) : defaultValue;
    }

    public static <K, V, R> R ifNotEmpty(Map<K, V> map, Function<Map<K, V>, R> func, R defaultValue) {
        return CollectionUtil.isNotEmpty(map) ? func.apply(map) : defaultValue;
    }

    public static <T, R> R apply(T obj, Function<T, R> func) {
        return func.apply(obj);
    }

    public static <T> void accept(T obj, Consumer<T> func) {
        func.accept(obj);
    }

    public static <T> BinaryOperator<T> getFirstFunction() {
        return (v1, v2) -> {
            return v1;
        };
    }

    public static <T> BinaryOperator<T> getLastFunction() {
        return (v1, v2) -> {
            return v1;
        };
    }
}
