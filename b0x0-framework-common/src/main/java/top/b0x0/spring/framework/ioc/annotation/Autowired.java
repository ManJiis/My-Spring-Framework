package top.b0x0.spring.framework.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Autowired
 *
 * TODO: 1.构造注入(Constructor Injection)2.设值方法注入(Setter Injection)3.接口注入(Interface Injection)
 * TODO: 目前采用接口注入
 * TODO: 构造注入会出现循环依赖
 *
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
    String value() default "";
}
