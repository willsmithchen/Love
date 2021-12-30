package com.lujia.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询方式 加上注解的属性为查询字段
 *
 * @author zenghao
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryField {

    /**
     * 查询方式 默认为全匹配
     *
     * @return Operator
     */
    Operator operator() default Operator.EQ;

    /**
     * 查询字段名 不填写默认为注解属性名
     *
     * @return String
     */
    String field() default "";

    /**
     * 是否查询空字符串
     *
     * @return boolean
     */
    boolean queryEmpty() default false;

    /**
     * 是否忽略该字段 默认不忽略
     *
     * @return boolean
     */
    boolean ignore() default false;

}
