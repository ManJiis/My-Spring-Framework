package top.b0x0.spring.framework.aop.advice.impl;

/**
 * 环绕通知接口AroundAdvice，这个接口继承了MethodBeforeAdvice,AfterReturningAdvice,ThrowsAdvice这三个接口，相当于这三个接口的合集。
 *
 * @author ManJiis
 * @since 2021-08-22
 */
public interface AroundAdvice extends BeforeAdvice, ReturnBeforeAdvice, ThrowsAdvice {
}
