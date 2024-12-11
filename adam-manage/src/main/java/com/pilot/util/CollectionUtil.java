
package com.pilot.util;

import cn.adam.util.funtion.Func;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CollectionUtil {
    public CollectionUtil() {
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return null != collection && !collection.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }

    public static <T> List<T> newEmptyList() {
        return Collections.emptyList();
    }

    public static <T> List<T> newSingletonList(T t) {
        return Collections.singletonList(t);
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }

    public static <T> List<T> newArrayList(T... values) {
        if (values != null && values.length != 0) {
            List<T> list = newArrayListWithExpectedSize(values.length);
            Collections.addAll(list, values);
            return list;
        } else {
            return Collections.emptyList();
        }
    }

    public static <T> T add(Collection<T> collection, T t) {
        if (collection == null) {
            throw new IllegalArgumentException("collection must not null!");
        } else {
            collection.add(t);
            return t;
        }
    }

    public static <T> T add(Collection<T> collection, Supplier<T> supplier) {
        if (collection == null) {
            throw new IllegalArgumentException("collection must not null!");
        } else {
            return add(collection, supplier.get());
        }
    }

    public static <T> ArrayList<T> newArrayListWithExpectedSize(int expectedSize) {
        return new ArrayList(expectedSize);
    }

    public static <K, V> Map<K, V> newEmptyMap() {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> newSingletonMap(K key, V value) {
        return Collections.singletonMap(key, value);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap(hashMapCapacity(expectedSize));
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithExpectedSize(int expectedSize) {
        return new ConcurrentHashMap(hashMapCapacity(expectedSize));
    }

    public static List<Long> listString2ListLong(List<String> strList) {
        if (isEmpty((Collection)strList)) {
            return newEmptyList();
        } else {
            List<Long> longList = newArrayListWithExpectedSize(strList.size());
            strList.forEach((d) -> {
                longList.add(Long.parseLong(d));
            });
            return longList;
        }
    }

    public static <T, K, U> Map<K, U> toMap(Collection<T> sourceCollection, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction) {
        return isEmpty(sourceCollection) ? Collections.emptyMap() : (Map)sourceCollection.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    public static <T, K> Map<K, T> toMap(Collection<T> sourceCollection, Function<? super T, ? extends K> keyMapper) {
        return toMap(sourceCollection, keyMapper, Function.identity(), Func.getFirstFunction());
    }

    public static <T, K, U> Map<K, U> toMap(Collection<T> sourceCollection, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        return toMap(sourceCollection, keyMapper, valueMapper, Func.getFirstFunction());
    }

    public static <T, K, V> Map<K, List<V>> groupBy(List<T> sourceList, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return (Map)(isEmpty((Collection)sourceList) ? Maps.newHashMap() : (Map)sourceList.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList()))));
    }

    public static <T, K> Map<K, List<T>> groupBy(List<T> sourceList, Function<T, K> keyMapper) {
        return groupBy(sourceList, keyMapper, Function.identity());
    }

    private static int hashMapCapacity(int expectedSize) {
        if (expectedSize < 0) {
            throw new IllegalArgumentException("不合法参数。");
        } else if (expectedSize < 3) {
            return expectedSize + 1;
        } else {
            return expectedSize < 1073741824 ? (int)((float)expectedSize / 0.75F + 1.0F) : Integer.MAX_VALUE;
        }
    }
}
