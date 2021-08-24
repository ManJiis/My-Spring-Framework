package top.b0x0.spring.framework.aop.advice.impl;

import top.b0x0.spring.framework.aop.advice.Advice;

import java.lang.reflect.Method;

/**
 * 前置通知接口BeforeAdvice，继承这个通知接口并实现其前置方法，可以前置增强目标类，即目标方法执行前会先执行这个前置方法
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public interface BeforeAdvice extends Advice {

    /**
     * 前置方法
     *
     * @param clz    目标类
     * @param method 目标方法
     * @param args   目标方法参数
     * @throws Throwable Throwable
     */
    void before(Class<?> clz, Method method, Object[] args) throws Throwable;
}
