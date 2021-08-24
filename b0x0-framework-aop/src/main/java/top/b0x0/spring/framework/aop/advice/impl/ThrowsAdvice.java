package top.b0x0.spring.framework.aop.advice.impl;

import top.b0x0.spring.framework.aop.advice.Advice;

import java.lang.reflect.Method;

/**
 * 异常通知接口ThrowsAdvice，继承这个通知接口并实现其异常方法，可以增强目标类的异常，即目标方法发生异常时，会执行这个异常方法
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public interface ThrowsAdvice extends Advice {

    /**
     * 异常方法
     *
     * @param clz    目标类
     * @param method 目标方法
     * @param args   目标方法参数
     * @param e      抛出异常
     */
    void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e);
}
