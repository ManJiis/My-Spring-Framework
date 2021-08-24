package top.b0x0.spring.framework.aop.advice.impl;

import top.b0x0.spring.framework.aop.advice.Advice;

import java.lang.reflect.Method;

/**
 * 后置通知接口AfterReturningAdvice，继承这个通知接口并实现其返回后方法，可以后置增强目标类，即目标方法执后并返回结果时，会执行这个返回方法。
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public interface AfterReturningAdvice extends Advice {

    /**
     * 返回后方法
     *
     * @param clz         目标类
     * @param returnValue 方法结果
     * @param method      目标方法
     * @param args        目标方法参数
     * @throws Throwable Throwable
     */
    void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable;
}
