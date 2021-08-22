package top.b0x0.spring.framework.aop.advice.impl;

import top.b0x0.spring.framework.aop.advice.Advice;

import java.lang.reflect.Method;

/**
 * 返回通知接口
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
