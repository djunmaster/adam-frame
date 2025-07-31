package com.pilot.tools.valid;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.pilot.exception.BusinessException;

import java.util.function.Supplier;
import java.math.BigDecimal;
import java.util.Objects;

public class BizAssert {
    public BizAssert() {
    }

    public static void isTrue(boolean expression, String errorMsgTemplate, Object... params) {
        if (!expression) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void notNull(Object obj, String errorMsgTemplate, Object... params) {
        if (ObjectUtil.isNull(obj)) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void isNull(Object obj, String errorMsgTemplate, Object... params) {
        if (ObjectUtil.isNotNull(obj)) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void notEmpty(Object obj, String errorMsgTemplate, Object... params) {
        if (ObjectUtil.isEmpty(obj)) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void isEmpty(Object obj, String errorMsgTemplate, Object... params) {
        if (ObjectUtil.isNotEmpty(obj)) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void isZero(Integer obj, String errorMsgTemplate, Object... params) {
        isTrue(obj == 0, errorMsgTemplate, params);
    }

    public static void isZero(BigDecimal obj, String errorMsgTemplate, Object... params) {
        isTrue(obj != null && obj.compareTo(BigDecimal.ZERO) == 0, errorMsgTemplate, params);
    }

    public static void eq(Object obj1, Object obj2, String errorMsgTemplate, Object... params) {
        if (!Objects.equals(obj1, obj2)) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void ne(Object obj1, Object obj2, String errorMsgTemplate, Object... params) {
        if (Objects.equals(obj1, obj2)) {
            throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static void tryIt(Runnable func, String errorMsg) {
        try {
            func.run();
        } catch (Exception var3) {
            throw new BusinessException(errorMsg, var3);
        }
    }

    public static <T> T tryItAndReturn(Supplier<T> func) {
        try {
            return func.get();
        } catch (Exception var2) {
            throw new BusinessException("出现错误", var2);
        }
    }

    public static <T> T tryItAndReturn(Supplier<T> func, String errorMsg) {
        try {
            return func.get();
        } catch (Exception var3) {
            throw new BusinessException(errorMsg, var3);
        }
    }

    public static void throwException(String errorMsgTemplate, Object... params) {
        throw new BusinessException(StrUtil.format(errorMsgTemplate, params));
    }
}