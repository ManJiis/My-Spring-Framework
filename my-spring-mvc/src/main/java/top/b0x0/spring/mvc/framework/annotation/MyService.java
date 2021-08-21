package top.b0x0.spring.mvc.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface MyService {
	String value() default "";
}
