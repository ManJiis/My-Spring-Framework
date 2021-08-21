package top.b0x0.spring.framework.webmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 请求的方法参数名
 *
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    /**
     * 方法参数别名
     */
    String value() default "";

    /**
     * 是否必传
     */
    boolean required() default true;
}
