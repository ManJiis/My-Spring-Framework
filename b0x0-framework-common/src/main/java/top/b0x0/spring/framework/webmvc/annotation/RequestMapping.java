package top.b0x0.spring.framework.webmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * http请求路径
 *
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
    /**
     * 请求路径
     *
     * @return /
     */
    String value() default "";

    /**
     * 请求方法
     */
    RequestMethodEnum method() default RequestMethodEnum.GET;
}
