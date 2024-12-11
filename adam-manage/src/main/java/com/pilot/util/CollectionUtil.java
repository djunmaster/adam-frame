package com.pilot.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CollectionUtil {

    // 检查集合是否为空
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    // 检查映射是否为空
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    // 检查集合是否非空
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    // 检查映射是否非空
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    // 创建一个空的不可变列表
    public static <T> List<T> newEmptyList() {
        return Collections.emptyList();
    }

    // 创建一个包含单个元素的不可变列表
    public static <T> List<T> newSingletonList(T t) {
        return Collections.singletonList(t);
    }

    // 创建一个新的空的 ArrayList
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    // 根据传入的元素创建一个新的 ArrayList
    @SafeVarargs
    public static <T> List<T> newArrayList(T... values) {
        return values == null || values.length == 0 ? Collections.emptyList() : new ArrayList<>(Arrays.asList(values));
    }

    // 向集合中添加元素，返回该元素
    public static <T> T add(Collection<T> collection, T t) {
        Objects.requireNonNull(collection, "Collection must not be null"); // 确保集合不为 null
        collection.add(t);
        return t;
    }

    // 使用 Supplier 生成的值向集合中添加元素
    public static <T> T add(Collection<T> collection, Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "Supplier must not be null"); // 确保 Supplier 不为 null
        return add(collection, supplier.get());
    }

    // 创建一个空的不可变映射
    public static <K, V> Map<K, V> newEmptyMap() {
        return Collections.emptyMap();
    }

    // 创建一个包含单个键值对的不可变映射
    public static <K, V> Map<K, V> newSingletonMap(K key, V value) {
        return Collections.singletonMap(key, value);
    }

    // 创建一个新的空的 HashMap
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    // 创建一个具有指定初始容量的 HashMap
    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap<>(hashMapCapacity(expectedSize));
    }

    // 创建一个具有指定初始容量的 ConcurrentHashMap
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithExpectedSize(int expectedSize) {
        return new ConcurrentHashMap<>(hashMapCapacity(expectedSize));
    }

    // 将字符串列表转换为长整型列表
    public static List<Long> listStringToListLong(List<String> strList) {
        return isEmpty(strList) ? newEmptyList() : strList.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    // 将集合转换为映射，支持自定义键和值映射逻辑以及冲突合并策略
    public static <T, K, U> Map<K, U> toMap(Collection<T> sourceCollection, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction) {
        return isEmpty(sourceCollection) ? Collections.emptyMap() : sourceCollection.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    // 将集合转换为映射，仅提供键映射逻辑，值为元素本身
    public static <T, K> Map<K, T> toMap(Collection<T> sourceCollection, Function<? super T, ? extends K> keyMapper) {
        return toMap(sourceCollection, keyMapper, Function.identity(), (u1, u2) -> u1);
    }

    // 将集合转换为映射，支持自定义键和值映射逻辑
    public static <T, K, U> Map<K, U> toMap(Collection<T> sourceCollection, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        return toMap(sourceCollection, keyMapper, valueMapper, (u1, u2) -> u1);
    }

    // 对列表进行分组，指定键和值的映射逻辑
    public static <T, K, V> Map<K, List<V>> groupBy(List<T> sourceList, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return isEmpty(sourceList) ? newHashMap() : sourceList.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    // 对列表进行分组，仅指定键的映射逻辑，值为元素本身
    public static <T, K> Map<K, List<T>> groupBy(List<T> sourceList, Function<? super T, ? extends K> keyMapper) {
        return groupBy(sourceList, keyMapper, Function.identity());
    }

    // 计算 HashMap 的初始容量，确保容量符合装载因子要求
    private static int hashMapCapacity(int expectedSize) {
        if (expectedSize < 0) {
            throw new IllegalArgumentException("Expected size must not be negative"); // 参数校验
        }
        return expectedSize < 3 ? expectedSize + 1 : (int) (expectedSize / 0.75f + 1); // 根据装载因子计算容量
    }
}
