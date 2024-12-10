package com.pilot.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.defaults.DefaultSqlSession.StrictMap;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MyBatis SQL拦截器，用于美化和打印SQL语句
 * 主要功能：
 * 1. 拦截SQL执行过程
 * 2. 替换SQL中的占位符
 * 3. 记录SQL执行耗时
 * 4. 格式化输出可读性更高的SQL语句
 */
@Intercepts(value = {
        @Signature(args = {Statement.class, ResultHandler.class}, method = "query", type = StatementHandler.class),
        @Signature(args = {Statement.class}, method = "update", type = StatementHandler.class),
        @Signature(args = {Statement.class}, method = "batch", type = StatementHandler.class)})
public class SqlBeautyInterceptor implements Interceptor {

    /**
     * 拦截SQL执行过程
     * 1. 计算SQL执行耗时
     * 2. 格式化SQL语句
     * 3. 打印格式化后的SQL和执行时间
     *
     * @param invocation MyBatis调用上下文
     * @return SQL执行结果
     * @throws Throwable 执行过程中可能抛出的异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取StatementHandler对象
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 记录开始执行时间
        long startTime = System.currentTimeMillis();

        try {
            // 执行原始SQL
            return invocation.proceed();
        } finally {
            // 计算SQL执行耗时
            long sqlCost = System.currentTimeMillis() - startTime;
            // 获取BoundSql对象
            BoundSql boundSql = statementHandler.getBoundSql();
            // 格式化SQL语句
            String sql = formatSql(
                    boundSql.getSql(),
                    boundSql.getParameterObject(),
                    boundSql.getParameterMappings()
            );
            // 打印格式化后的SQL和执行时间
            System.out.println("SQL： [ " + sql + " ]执行耗时[ " + sqlCost + "ms ]");
        }
    }

    /**
     * 创建插件代理对象
     *
     * @param target 目标对象
     * @return 代理后的对象
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置插件属性（预留方法）
     *
     * @param properties 属性配置
     */
    @Override
    public void setProperties(Properties properties) {
        // 空实现，预留扩展接口
    }

    /**
     * 格式化SQL语句
     * 1. 处理空SQL
     * 2. 替换SQL中的占位符
     *
     * @param sql 原始SQL语句
     * @param parameterObject 参数对象
     * @param parameterMappingList 参数映射列表
     * @return 格式化后的SQL语句
     */
    private String formatSql(String sql, Object parameterObject, List<ParameterMapping> parameterMappingList) {
        // 处理空SQL或无参数场景
        if (sql.isEmpty() || ObjectUtil.isNull(parameterObject) || CollectionUtil.isEmpty(parameterMappingList)) {
            return beautifySql(sql);
        }

        try {
            // 替换SQL占位符
            return replacePlaceholders(sql, parameterObject, parameterMappingList);
        } catch (Exception e) {
            // 发生异常时返回原始美化SQL
            return beautifySql(sql);
        }
    }

    /**
     * 根据参数类型替换SQL中的占位符
     *
     * @param sql 原始SQL语句
     * @param parameterObject 参数对象
     * @param parameterMappingList 参数映射列表
     * @return 替换占位符后的SQL语句
     * @throws Exception 反射或参数处理过程中可能抛出的异常
     */
    private String replacePlaceholders(String sql, Object parameterObject, List<ParameterMapping> parameterMappingList) throws Exception {
        Class<?> parameterObjectClass = parameterObject.getClass();

        // 处理StrictMap（MyBatis内部类）
        if (isStrictMap(parameterObjectClass)) {
            StrictMap<Collection<?>> strictMap = (StrictMap<Collection<?>>) parameterObject;
            if (isList(strictMap.get("list").getClass())) {
                return replaceListParameters(sql, strictMap.get("list"));
            }
        }
        // 处理Map类型参数
        else if (isMap(parameterObjectClass)) {
            return replaceMapParameters(sql, (Map<?, ?>) parameterObject, parameterMappingList);
        }

        // 处理普通对象类型参数
        return replaceCommonParameters(sql, parameterMappingList, parameterObject);
    }

    /**
     * 替换普通对象参数的SQL占位符
     *
     * @param sql 原始SQL语句
     * @param parameterMappingList 参数映射列表
     * @param parameterObject 参数对象
     * @return 替换占位符后的SQL语句
     * @throws Exception 反射处理过程中可能抛出的异常
     */
    private String replaceCommonParameters(String sql, List<ParameterMapping> parameterMappingList, Object parameterObject) throws Exception {
        Class<?> parameterObjectClass = parameterObject.getClass();

        // 处理基本类型和包装类
        if (isPrimitiveOrPrimitiveWrapper(parameterObjectClass)) {
            return sql.replaceFirst("\\?", parameterObject.toString());
        }

        // 获取对象所有字段（包括父类字段）
        List<Field> allFields = getAllFields(parameterObjectClass);

        // 遍历参数映射，替换占位符
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Field field = findFieldByName(allFields, parameterMapping.getProperty());
            if (field != null) {
                field.setAccessible(true);
                Object value = field.get(parameterObject);
                String propertyValue = formatParameterValue(value, parameterMapping);
                sql = sql.replaceFirst("\\?", propertyValue);
            }
        }

        return sql;
    }

    /**
     * 替换Map类型参数的SQL占位符
     *
     * @param sql 原始SQL语句
     * @param paramMap 参数Map
     * @param parameterMappingList 参数映射列表
     * @return 替换占位符后的SQL语句
     */
    private String replaceMapParameters(String sql, Map<?, ?> paramMap, List<ParameterMapping> parameterMappingList) {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Object propertyName = parameterMapping.getProperty();
            Object propertyValue = paramMap.get(propertyName);

            if (propertyValue != null) {
                String formattedValue = formatParameterValue(propertyValue, parameterMapping);
                sql = sql.replaceFirst("\\?", formattedValue);
            }
        }
        return sql;
    }

    /**
     * 替换List类型参数的SQL占位符
     *
     * @param sql 原始SQL语句
     * @param col 参数集合
     * @return 替换占位符后的SQL语句
     */
    private String replaceListParameters(String sql, Collection<?> col) {
        if (CollectionUtil.isEmpty(col)) {
            return sql;
        }

        for (Object obj : col) {
            String value = formatParameterValue(obj, null);
            sql = sql.replaceFirst("\\?", value);
        }
        return sql;
    }

    /**
     * 格式化参数值
     * 1. 处理null值
     * 2. 处理基本类型
     * 3. 处理字符串类型
     *
     * @param value 参数值
     * @param parameterMapping 参数映射
     * @return 格式化后的参数值字符串
     */
    private String formatParameterValue(Object value, ParameterMapping parameterMapping) {
        // 处理null值
        if (value == null) {
            return "null";
        }

        // 处理基本类型和包装类
        if (isPrimitiveOrPrimitiveWrapper(value.getClass())) {
            return value.toString();
        }

        // 处理字符串类型
        if (parameterMapping != null && parameterMapping.getJavaType().isAssignableFrom(String.class)) {
            return "\"" + value.toString() + "\"";
        }

        return value.toString();
    }

    /**
     * 获取类及其父类的所有字段
     *
     * @param clazz 目标类
     * @return 字段列表
     */
    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> allFields = new ArrayList<>();
        while (clazz != null) {
            allFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return allFields;
    }

    /**
     * 根据字段名查找字段
     *
     * @param fields 字段列表
     * @param name 字段名
     * @return 匹配的字段，未找到返回null
     */
    private Field findFieldByName(List<Field> fields, String name) {
        return fields.stream()
                .filter(field -> field.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 美化SQL语句（去除多余空白）
     *
     * @param sql 原始SQL语句
     * @return 美化后的SQL语句
     */
    private String beautifySql(String sql) {
        return sql.replaceAll("[\\s\n ]+", " ");
    }

    /**
     * 判断是否为基本类型或其包装类
     *
     * @param clazz 待判断的类
     * @return 是否为基本类型或包装类
     */
    private boolean isPrimitiveOrPrimitiveWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
                Arrays.asList(
                        Byte.class, Short.class, Integer.class, Long.class,
                        Double.class, Float.class, Character.class, Boolean.class
                ).contains(clazz);
    }

    /**
     * 判断是否为StrictMap类型
     *
     * @param parameterObjectClass 待判断的类
     * @return 是否为StrictMap
     */
    private boolean isStrictMap(Class<?> parameterObjectClass) {
        return StrictMap.class.isAssignableFrom(parameterObjectClass);
    }

    /**
     * 判断是否为List类型
     *
     * @param clazz 待判断的类
     * @return 是否为List
     */
    private boolean isList(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    /**
     * 判断是否为Map类型
     *
     * @param parameterObjectClass 待判断的类
     * @return 是否为Map
     */
    private boolean isMap(Class<?> parameterObjectClass) {
        return Map.class.isAssignableFrom(parameterObjectClass);
    }
}