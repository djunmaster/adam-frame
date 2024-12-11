package com.pilot.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public interface IDictEnum {

    /**
     * 缓存枚举值
     */
    Map<Class<?>, Map<Integer, Enum<?>>> ENUM_CACHE = new ConcurrentHashMap<>();

    Integer getType();

    String getDesc();

    /**
     * 根据类型获取枚举值
     */
    static <E extends Enum<E> & IDictEnum> E getEnum(Integer type, Class<E> clazz) {
        if (type == null || clazz == null) {
            return null;
        }

        return clazz.cast(
                ENUM_CACHE
                        .computeIfAbsent(clazz, k -> new ConcurrentHashMap<>())
                        .computeIfAbsent(type, t -> EnumSet.allOf(clazz).stream()
                                .filter(e -> e.getType().equals(type))
                                .findFirst()
                                .orElse(null))
        );
    }

    /**
     * 根据类型字符串获取枚举值
     */
    static <E extends Enum<E> & IDictEnum> E getEnum(String type, Class<E> clazz) {
        try {
            return getEnum(Integer.parseInt(type), clazz);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 获取枚举值，若为空则返回默认值
     */
    static <E extends Enum<E> & IDictEnum> E defaultIfNull(Integer type, Class<E> clazz, E defaultDict) {
        return ObjectUtil.defaultIfNull(getEnum(type, clazz), defaultDict);
    }

    /**
     * 根据描述获取枚举值
     */
    static <E extends Enum<E> & IDictEnum> E getEnumByDesc(String desc, Class<E> clazz) {
        if (desc == null || clazz == null) {
            return null;
        }
        return EnumSet.allOf(clazz).stream()
                .filter(e -> e.getDesc().equals(desc))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据类型获取描述
     */
    static <E extends Enum<E> & IDictEnum> String getDescByType(Integer type, Class<E> clazz) {
        return Optional.ofNullable(getEnum(type, clazz))
                .map(IDictEnum::getDesc)
                .orElse("未知");
    }

    /**
     * 判断两个枚举是否相等
     */
    static boolean eq(IDictEnum e1, IDictEnum e2) {
        return Objects.equals(e1, e2);
    }

    /**
     * 判断类型和枚举是否相等
     */
    static boolean eq(Integer type, IDictEnum e) {
        return e != null && Objects.equals(type, e.getType());
    }

    /**
     * 判断两个枚举是否不相等
     */
    static boolean ne(IDictEnum e1, IDictEnum e2) {
        return !eq(e1, e2);
    }

    /**
     * 判断类型和枚举是否不相等
     */
    static boolean ne(Integer type, IDictEnum e) {
        return !eq(type, e);
    }

    /**
     * 判断指定类型对应的枚举是否为空
     */
    static <E extends Enum<E> & IDictEnum> boolean isNull(Integer type, Class<E> clazz) {
        return getEnum(type, clazz) == null;
    }

    /**
     * 如果相等则执行操作
     */
    static void handleIfEqual(Integer type, IDictEnum e, Runnable func) {
        if (eq(type, e)) {
            func.run();
        }
    }

    /**
     * 如果两个枚举相等则执行操作
     */
    static void handleIfEqual(IDictEnum e1, IDictEnum e2, Runnable func) {
        if (eq(e1, e2)) {
            func.run();
        }
    }

    /**
     * 枚举与类型匹配时执行操作
     */
    default void handleIf(Integer type, Runnable func) {
        if (eq(type, this)) {
            func.run();
        }
    }

    /**
     * 枚举与指定值匹配时执行操作
     */
    default void handleIf(IDictEnum dict, Runnable func) {
        if (eq(dict, this)) {
            func.run();
        }
    }

    /**
     * 创建匹配器
     */
    static DictMatcher match(IDictEnum dict) {
        return new DictMatcher(dict);
    }

    /**
     * 创建带返回值的枚举匹配用例
     */
    static <R> DictSupplierCase<R> dictCase(IDictEnum value, Supplier<R> func) {
        return new DictSupplierCase<>(value, func);
    }

    /**
     * 创建无返回值的枚举匹配用例
     */
    static DictRunnableCase dictCase(IDictEnum value, Runnable func) {
        return new DictRunnableCase(value, func);
    }

    /**
     * 带返回值的匹配用例
     */
    class DictSupplierCase<R> {
        @Getter
        private final IDictEnum value;
        private final Supplier<R> func;

        private DictSupplierCase(IDictEnum value, Supplier<R> func) {
            this.value = value;
            this.func = func;
        }

        public R run() {
            return func.get();
        }
    }

    /**
     * 无返回值的匹配用例
     */
    class DictRunnableCase {
        private final Runnable func;

        private DictRunnableCase(IDictEnum value, Runnable func) {
            this.func = func;
        }

        public void run() {
            func.run();
        }
    }

    /**
     * 枚举值匹配器
     */
    class DictMatcher {
        private final IDictEnum value;

        private DictMatcher(IDictEnum value) {
            this.value = value;
        }

        /**
         * 根据用例获取返回值
         */
        @SafeVarargs
        public final <R> R of(DictSupplierCase<? extends R>... dictCases) {
            return Arrays.stream(dictCases)
                    .filter(dictCase -> eq(value, dictCase.getValue()))
                    .findFirst()
                    .map(DictSupplierCase::run)
                    .orElse(null);
        }
    }
}
