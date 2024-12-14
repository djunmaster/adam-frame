package com.pilot.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public final class BigDecimalUtil {
    private BigDecimalUtil() {} // 防止实例化

    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal ZERO_POINT_ZERO_ONE = new BigDecimal("0.01");
    public static final BigDecimal ZERO_POINT_ONE = new BigDecimal("0.1");
    public static final BigDecimal ONE = BigDecimal.ONE;
    public static final BigDecimal TEN = BigDecimal.TEN;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    // 缩放方法
    public static BigDecimal scale(BigDecimal value, int scale, RoundingMode mode) {
        return Optional.ofNullable(value)
                .map(v -> v.setScale(scale, mode))
                .orElse(null);
    }

    public static BigDecimal setScaleDown(BigDecimal value, int scale) {
        return scale(value, scale, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal setScaleHalfUp(BigDecimal value, int scale) {
        return scale(value, scale, RoundingMode.HALF_UP);
    }

    // 转换方法
    public static BigDecimal string2BigDecimal(String value) {
        return string2BigDecimal(value, null);
    }

    public static BigDecimal string2BigDecimal(String value, BigDecimal defaultValue) {
        try {
            return StringUtil.isEmpty(value) ? defaultValue : new BigDecimal(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    // 基本运算
    public static BigDecimal operate(BigDecimal a, BigDecimal b, BinaryOperator<BigDecimal> operator) {
        return operator.apply(Optional.ofNullable(a).orElse(ZERO),
                Optional.ofNullable(b).orElse(ZERO));
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return operate(a, b, BigDecimal::multiply);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return operate(a, b, BigDecimal::subtract);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode mode) {
        return Optional.ofNullable(a)
                .flatMap(x -> Optional.ofNullable(b)
                        .map(y -> x.divide(y, scale, mode)))
                .orElse(null);
    }

    // 求和方法
    public static BigDecimal add(BigDecimal... values) {
        return Stream.of(Optional.ofNullable(values).orElse(new BigDecimal[0]))
                .map(v -> Optional.ofNullable(v).orElse(ZERO))
                .reduce(ZERO, BigDecimal::add);
    }

    public static BigDecimal add(String... values) {
        return Stream.of(Optional.ofNullable(values).orElse(new String[0]))
                .map(v -> string2BigDecimal(v, ZERO))
                .reduce(ZERO, BigDecimal::add);
    }

    public static BigDecimal add(Collection<BigDecimal> values) {
        return Optional.ofNullable(values)
                .map(Collection::stream)
                .orElse(Stream.empty())
                .map(v -> Optional.ofNullable(v).orElse(ZERO))
                .reduce(ZERO, BigDecimal::add);
    }

    // 转换方法
    public static int changeY2F(BigDecimal amount) {
        return amount.multiply(ONE_HUNDRED).intValue();
    }

    public static BigDecimal requireNonNull(BigDecimal value) {
        return requireNonNull(value, ZERO);
    }

    public static BigDecimal requireNonNull(BigDecimal value, BigDecimal defaultValue) {
        Assert.notNull(defaultValue, "默认值不能为null");
        return Optional.ofNullable(value).orElse(defaultValue);
    }

    // 判断方法
    public static boolean isIntegerValue(BigDecimal value) {
        return Optional.ofNullable(value)
                .map(v -> v.scale() <= 0 || v.stripTrailingZeros().scale() <= 0)
                .orElse(false);
    }

    public static BigDecimal max(BigDecimal... values) {
        return Stream.of(Optional.ofNullable(values).orElse(new BigDecimal[0]))
                .filter(ObjectUtil::isNotNull)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    // 格式化方法
    public static String format2string(BigDecimal value) {
        return Optional.ofNullable(value)
                .map(v -> v.stripTrailingZeros().toPlainString())
                .orElse(null);
    }

    // 比较方法
    public static boolean gt(BigDecimal a, BigDecimal b) {
        return compare(a, b, x -> x > 0);
    }

    public static boolean ge(BigDecimal a, BigDecimal b) {
        return compare(a, b, x -> x >= 0);
    }

    public static boolean lt(BigDecimal a, BigDecimal b) {
        return compare(a, b, x -> x < 0);
    }

    public static boolean le(BigDecimal a, BigDecimal b) {
        return compare(a, b, x -> x <= 0);
    }

    public static boolean eq(BigDecimal a, BigDecimal b) {
        if (a == null) return b == null;
        return b != null && a.compareTo(b) == 0;
    }

    private static boolean compare(BigDecimal a, BigDecimal b, java.util.function.IntPredicate predicate) {
        return Optional.ofNullable(a)
                .flatMap(x -> Optional.ofNullable(b)
                        .map(y -> predicate.test(x.compareTo(y))))
                .orElse(false);
    }

    // 零值比较
    public static boolean eqZero(BigDecimal value) { return eq(value, ZERO); }
    public static boolean neZero(BigDecimal value) { return !eq(value, ZERO); }
    public static boolean gtZero(BigDecimal value) { return gt(value, ZERO); }
    public static boolean geZero(BigDecimal value) { return ge(value, ZERO); }
    public static boolean ltZero(BigDecimal value) { return lt(value, ZERO); }
    public static boolean leZero(BigDecimal value) { return le(value, ZERO); }

    // 其他工具方法
    public static BigDecimal trimZero(BigDecimal value) {
        return Optional.ofNullable(value)
                .map(v -> new BigDecimal(format2string(v)))
                .orElse(null);
    }

    public static String erasureScientificNotation(String source) {
        if (StringUtil.isEmpty(source) || !source.contains("E+")) {
            return source;
        }
        try {
            Double.parseDouble(source);
            return format2string(new BigDecimal(source));
        } catch (Exception e) {
            return source;
        }
    }

    public static String erasureScientificNotation(Double source) {
        return Optional.ofNullable(source)
                .map(String::valueOf)
                .map(str -> {
                    try {
                        return format2string(new BigDecimal(str));
                    } catch (Exception e) {
                        return String.valueOf(source);
                    }
                })
                .orElse(null);
    }

    public static boolean judgePosition(BigDecimal value, int position) {
        return eq(value, setScaleDown(value, position));
    }

    // 常用的两位小数处理方法
    public static BigDecimal down2Places(BigDecimal value) {
        return scale(value, 2, RoundingMode.DOWN);
    }

    public static BigDecimal halfUp2Places(BigDecimal value) {
        return scale(value, 2, RoundingMode.HALF_UP);
    }

    public static String down2PlacesString(BigDecimal value) {
        return Optional.ofNullable(down2Places(value))
                .map(BigDecimalUtil::format2string)
                .orElse(null);
    }
}