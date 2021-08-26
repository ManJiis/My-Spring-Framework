package top.b0x0.spring.sample.aspect;

import lombok.extern.slf4j.Slf4j;
import top.b0x0.spring.framework.aop.advice.impl.AroundAdvice;
import top.b0x0.spring.framework.aop.annotation.Aspect;
import top.b0x0.spring.framework.aop.annotation.AspectOrder;

import java.lang.reflect.Method;

/**
 * @author zzzzbw
 * @since 2018/6/6 17:00
 */
@Aspect(pointcut = "@annotation(top.b0x0.spring.sample.annotation.TestAnnotation)")
@AspectOrder(1)
@Slf4j
public class TestAspect implements AroundAdvice {

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        log.info("-----------before  TestAspect-----------");
        log.info("class: {}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void returnBefore(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        log.info("-----------after  TestAspect-----------");
        log.info("class: {}, method: {}", clz, method.getName());
    }

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        log.info("-----------error  TestAspect-----------");
        log.info("class: {}, method: {}, exception: {}", clz, method.getName(), e.getMessage());
    }
}
