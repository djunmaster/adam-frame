package com.pilot.util;

import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Component;

/**
 * 校验工具类，用于基于 Hibernate Validator 进行数据校验。
 */
@Component
public class ValidUtil {
    @Resource
    private Validator validator;

    /**
     * 默认构造函数，用于依赖注入。
     */
    public ValidUtil() {
    }

    /**
     * 校验指定对象是否符合验证规则。
     *
     * @param object 要校验的对象
     * @param groups 校验分组（可选参数）
     * @param <T>    被校验对象的类型
     * @return 如果校验失败，返回包含约束违反信息的集合；如果校验通过，返回空集合
     * @throws IllegalArgumentException 如果传入对象为空
     */
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        if (ObjectUtil.isNull(object)) {
            throw new IllegalArgumentException("校验对象不能为空。");
        }
        return this.validator.validate(object, groups);
    }

    /**
     * 校验指定类的某个属性值是否符合验证规则。
     *
     * @param beanType    要校验的类的类型
     * @param propertyName 要校验的属性名
     * @param value       要校验的属性值
     * @param groups      校验分组（可选参数）
     * @param <T>         类的类型
     * @return 如果校验失败，返回包含约束违反信息的集合；如果校验通过，返回空集合
     * @throws IllegalArgumentException 如果类、属性名或值为空
     */
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        if (beanType == null || propertyName == null || value == null) {
            throw new IllegalArgumentException("校验类、属性名和值不能为空。");
        }
        return this.validator.validateValue(beanType, propertyName, value, groups);
    }

    /**
     * 校验对象，并返回错误提示信息（以逗号分隔）。
     *
     * @param object 要校验的对象
     * @param groups 校验分组（可选参数）
     * @param <T>    被校验对象的类型
     * @return 如果校验失败，返回错误信息字符串；如果校验通过，返回空字符串
     */
    public <T> String validAndReturnErrorTips(T object, Class<?>... groups) {
        if (object == null) {
            return "校验对象不能为空。";
        }
        return this.validator.validate(object, groups).stream()
                .map(constraint -> constraint.getPropertyPath() + " " + constraint.getMessage())
                .collect(Collectors.joining(", "));
    }

    /**
     * 校验对象（默认分组），并返回错误提示信息（以逗号分隔）。
     *
     * @param object 要校验的对象
     * @param <T>    被校验对象的类型
     * @return 如果校验失败，返回错误信息字符串；如果校验通过，返回空字符串
     */
    public <T> String validAndReturnErrorTips(T object) {
        return validAndReturnErrorTips(object, Default.class);
    }
}
