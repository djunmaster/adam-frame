//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.tools.enums;

import cn.hutool.core.util.ObjectUtil;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.pilot.tools.functions.Func;
import lombok.Getter;
import lombok.NonNull;

public interface IDictEnum {
    Map<Class<?>, Map<Integer, Enum<?>>> enumCache = new ConcurrentHashMap();

    Integer getType();

    String getDesc();

    static <E extends Enum<E> & IDictEnum> E getEnum(Integer type, Class<E> clazz) {
        if (type != null && clazz != null) {
            Map<Integer, Enum<?>> cachedEnums = enumCache.computeIfAbsent(clazz, (k) -> new ConcurrentHashMap());
            Enum<?> cachedEnum = (Enum)cachedEnums.get(type);
            if (cachedEnum != null) {
                return clazz.cast(cachedEnum);
            } else {
                EnumSet<E> all = EnumSet.allOf(clazz);
                E result = all.stream().filter((e) -> e.getType().equals(type)).findFirst().orElse(null);
                if (result != null) {
                    cachedEnums.put(type, result);
                }

                return result;
            }
        } else {
            return null;
        }
    }

    static <E extends Enum<E> & IDictEnum> E getEnum(String type, Class<E> clazz) {
        return getEnum(Integer.parseInt(type), clazz);
    }

    static <E extends Enum<E> & IDictEnum> E defaultIfNull(Integer type, Class<E> clazz, E defaultDict) {
        return ObjectUtil.defaultIfNull(getEnum(type, clazz), defaultDict);
    }

    static <E extends Enum<E> & IDictEnum> E getEnumByDesc(String desc, Class<E> clazz) {
        if (desc != null && clazz != null) {
            EnumSet<E> all = EnumSet.allOf(clazz);
            return all.stream().filter((e) -> e.getDesc().equals(desc)).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    static <E extends Enum<E> & IDictEnum> String getDescByType(Integer type, Class<E> clazz) {
        E dict = getEnum(type, clazz);
        return dict == null ? "未知" : ((IDictEnum)dict).getDesc();
    }

    static boolean eq(IDictEnum e1, IDictEnum e2) {
        if (ObjectUtil.isNull(e1) && ObjectUtil.isNull(e2)) {
            return true;
        } else {
            return !ObjectUtil.isNull(e1) && !ObjectUtil.isNull(e2) && e1.equals(e2);
        }
    }

    static boolean eq(Integer type, IDictEnum e) {
        if (ObjectUtil.isNull(type) && ObjectUtil.isNull(e)) {
            return true;
        } else {
            return !ObjectUtil.isNull(type) && !ObjectUtil.isNull(e) && type.equals(e.getType());
        }
    }

    static boolean ne(@NonNull IDictEnum e1, @NonNull IDictEnum e2) {
        if (e1 == null) {
            throw new NullPointerException("e1 is marked non-null but is null");
        } else if (e2 == null) {
            throw new NullPointerException("e2 is marked non-null but is null");
        } else {
            return !eq(e1, e2);
        }
    }

    static boolean ne(Integer type, IDictEnum e) {
        return !eq(type, e);
    }

    static <E extends Enum<E> & IDictEnum> boolean isNull(Integer type, Class<E> clazz) {
        return ObjectUtil.isNull(getEnum(type, clazz));
    }

    static void handleIfEqual(Integer type, IDictEnum e, Runnable func) {
        if (eq(type, e)) {
            func.run();
        }

    }

    static void handleIfEqual(IDictEnum e1, IDictEnum e2, Runnable func) {
        if (eq(e1, e2)) {
            func.run();
        }

    }

    default void handleIf(Integer type, Runnable func) {
        if (eq(type, this)) {
            func.run();
        }

    }

    default void handleIf(IDictEnum dict, Runnable func) {
        if (eq(dict, this)) {
            func.run();
        }

    }

    static DictMatcher match(IDictEnum dict) {
        return new DictMatcher(dict);
    }

    static <R> DictSupplierCase<R> dictCase(IDictEnum value, Supplier<R> func) {
        return new DictSupplierCase(value, func);
    }

    static DictRunnableCase dictCase(IDictEnum value, Runnable func) {
        return new DictRunnableCase(value, func);
    }

    public static class DictRunnableCase {
        private final Runnable func;

        private DictRunnableCase(IDictEnum value, Runnable func) {
            this.func = func;
        }

        public void run() {
            this.func.run();
        }

        public IDictEnum getValue() {
            return null;
        }
    }

    public static class DictSupplierCase<R> {
        @Getter
        private final IDictEnum value;
        private final Supplier<R> func;

        private DictSupplierCase(IDictEnum value, Supplier<R> func) {
            this.value = value;
            this.func = func;
        }

        public R run() {
            return this.func.get();
        }
    }

    public static class DictMatcher {
        private final IDictEnum value;

        private DictMatcher(IDictEnum value) {
            this.value = value;
        }

        @SafeVarargs
        public final <R> R of(DictSupplierCase<? extends R>... dictCases) {
            int var3 = dictCases.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                DictSupplierCase<? extends R> dictCase = ((DictSupplierCase[]) dictCases)[var4];
                if (IDictEnum.eq(this.value, dictCase.getValue())) {
                    return dictCase.run();
                }
            }

            return null;
        }

        public final void of(DictRunnableCase... dictCases) {
            Arrays.stream(dictCases).forEach((dictCase) -> {
                Func.ifTrue(IDictEnum.eq(this.value, dictCase.getValue()), dictCase::run);
            });
        }
    }
}
