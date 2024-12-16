package com.pilot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BeanConvertUtil extends BeanUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanConvertUtil.class);

    private BeanConvertUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 将源对象转换为目标对象
     *
     * @param <S> 源对象的类型
     * @param <T> 目标对象的类型
     * @param source 源对象
     * @param targetSupplier 提供目标对象实例的 Supplier
     * @return 转换后的目标对象，如果源对象或目标对象为空则返回 null
     */
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier) {
        if (source == null || targetSupplier == null) {
            return null;
        }
        T target = targetSupplier.get();
        copyProperties(source, target);
        return target;
    }

    /**
     * 将源对象转换为目标对象，支持源对象和目标对象的回调处理
     *
     * @param <S> 源对象的类型
     * @param <T> 目标对象的类型
     * @param source 源对象
     * @param targetSupplier 提供目标对象实例的 Supplier
     * @param callBack 处理源对象和目标对象的回调函数
     * @return 转换后的目标对象，如果源对象或目标对象为空则返回 null
     */
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        T target = convertTo(source, targetSupplier);
        if (target != null && callBack != null) {
            callBack.callBack(source, target);
        }
        return target;
    }

    /**
     * 将源对象转换为目标对象，支持目标对象的回调处理
     *
     * @param <S> 源对象的类型
     * @param <T> 目标对象的类型
     * @param source 源对象
     * @param targetSupplier 提供目标对象实例的 Supplier
     * @param callBack 处理目标对象的回调函数
     * @return 转换后的目标对象，如果源对象或目标对象为空则返回 null
     */
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ResultConvertCallBack<T> callBack) {
        T target = convertTo(source, targetSupplier);
        if (target != null && callBack != null) {
            callBack.callBack(target);
        }
        return target;
    }

    /**
     * 将源对象集合转换为目标对象集合
     *
     * @param <S> 源对象的类型
     * @param <T> 目标对象的类型
     * @param sources 源对象集合
     * @param targetSupplier 提供目标对象实例的 Supplier
     * @return 转换后的目标对象集合，如果源集合为空则返回空集合
     */
    public static <S, T> List<T> convertListTo(Collection<S> sources, Supplier<T> targetSupplier) {
        if (sources == null || targetSupplier == null || sources.isEmpty()) {
            return Collections.emptyList();
        }
        return sources.stream().map(source -> convertTo(source, targetSupplier)).collect(Collectors.toList());
    }

    /**
     * 将源对象集合转换为目标对象集合，支持源对象和目标对象的回调处理
     *
     * @param <S> 源对象的类型
     * @param <T> 目标对象的类型
     * @param sources 源对象集合
     * @param targetSupplier 提供目标对象实例的 Supplier
     * @param callBack 处理源对象和目标对象的回调函数
     * @return 转换后的目标对象集合，如果源集合为空则返回空集合
     */
    public static <S, T> List<T> convertListTo(Collection<S> sources, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        if (sources == null || targetSupplier == null || sources.isEmpty()) {
            return Collections.emptyList();
        }
        return sources.stream()
                .map(source -> convertTo(source, targetSupplier, callBack))
                .collect(Collectors.toList());
    }

    /**
     * 将源对象集合转换为目标对象集合，支持目标对象的回调处理
     *
     * @param <S> 源对象的类型
     * @param <T> 目标对象的类型
     * @param sources 源对象集合
     * @param targetSupplier 提供目标对象实例的 Supplier
     * @param callBack 处理目标对象的回调函数
     * @return 转换后的目标对象集合，如果源集合为空则返回空集合
     */
    public static <S, T> List<T> convertListTo(Collection<S> sources, Supplier<T> targetSupplier, ResultConvertCallBack<T> callBack) {
        if (sources == null || targetSupplier == null || sources.isEmpty()) {
            return Collections.emptyList();
        }
        return sources.stream()
                .map(source -> convertTo(source, targetSupplier, callBack))
                .collect(Collectors.toList());
    }

    /**
     * 将源对象转换为目标对象，如果源对象或目标对象为空抛出异常
     *
     * @param <T> 目标对象的类型
     * @param source 源对象
     * @param targetSupplier 提供目标对象实例的 Supplier
     * @return 转换后的目标对象
     * @throws NullPointerException 如果源对象或目标对象为空，抛出空指针异常
     */
    public static <T> T convertObjTo(Object source, Supplier<T> targetSupplier) {
        Objects.requireNonNull(source, "Source object must not be null");
        Objects.requireNonNull(targetSupplier, "Target supplier must not be null");
        T target = targetSupplier.get();
        copyProperties(source, target);
        return target;
    }

    /**
     * 回调接口，用于在转换过程中处理源对象和目标对象
     */
    @FunctionalInterface
    public interface ConvertCallBack<S, T> {
        void callBack(S source, T target);
    }

    /**
     * 回调接口，用于在转换过程中处理目标对象
     */
    @FunctionalInterface
    public interface ResultConvertCallBack<T> {
        void callBack(T target);
    }
}
