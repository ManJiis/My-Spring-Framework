package top.b0x0.spring.framework.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import top.b0x0.spring.framework.aop.advice.impl.BeforeAdvice;
import top.b0x0.spring.framework.aop.advice.impl.ReturnBeforeAdvice;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author ManJiis
 * @since 2021-08-26
 */
@Slf4j
public class SimpleReturnBeforeAspect implements ReturnBeforeAdvice, Serializable {
    private static final long serialVersionUID = 1L;

    public SimpleReturnBeforeAspect() {
    }

    @Override
    public void returnBefore(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        log.info("[b0x0 framework] do simple return before aspect ! ");
    }
}