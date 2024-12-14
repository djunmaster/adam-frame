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

    // 单个对象转换
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier) {
        if (source == null || targetSupplier == null) return null;
        T target = targetSupplier.get();
        copyProperties(source, target);
        return target;
    }

    // 单个对象转换，支持回调处理源和目标对象
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        T target = convertTo(source, targetSupplier);
        if (target != null && callBack != null) {
            callBack.callBack(source, target);
        }
        return target;
    }

    // 单个对象转换，支持回调处理目标对象
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ResultConvertCallBack<T> callBack) {
        T target = convertTo(source, targetSupplier);
        if (target != null && callBack != null) {
            callBack.callBack(target);
        }
        return target;
    }

    // 列表转换
    public static <S, T> List<T> convertListTo(Collection<S> sources, Supplier<T> targetSupplier) {
        if (sources == null || targetSupplier == null || sources.isEmpty()) return Collections.emptyList();
        return sources.stream().map(source -> convertTo(source, targetSupplier)).collect(Collectors.toList());
    }

    // 列表转换，支持回调处理源和目标对象
    public static <S, T> List<T> convertListTo(Collection<S> sources, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        if (sources == null || targetSupplier == null || sources.isEmpty()) return Collections.emptyList();
        return sources.stream()
                .map(source -> convertTo(source, targetSupplier, callBack))
                .collect(Collectors.toList());
    }

    // 列表转换，支持回调处理目标对象
    public static <S, T> List<T> convertListTo(Collection<S> sources, Supplier<T> targetSupplier, ResultConvertCallBack<T> callBack) {
        if (sources == null || targetSupplier == null || sources.isEmpty()) return Collections.emptyList();
        return sources.stream()
                .map(source -> convertTo(source, targetSupplier, callBack))
                .collect(Collectors.toList());
    }

    // 通用对象转换，抛出异常处理
    public static <T> T convertObjTo(Object source, Supplier<T> targetSupplier) {
        Objects.requireNonNull(source, "Source object must not be null");
        Objects.requireNonNull(targetSupplier, "Target supplier must not be null");
        T target = targetSupplier.get();
        copyProperties(source, target);
        return target;
    }

    @FunctionalInterface
    public interface ResultConvertCallBack<T> {
        void callBack(T target);
    }

    @FunctionalInterface
    public interface ConvertCallBack<S, T> {
        void callBack(S source, T target);
    }
}
