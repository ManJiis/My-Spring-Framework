package top.b0x0.spring.framework.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import top.b0x0.spring.framework.aop.advice.impl.BeforeAdvice;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author ManJiis
 * @since 2021-08-26
 */
@Slf4j
public class SimpleBeforeAspect implements BeforeAdvice, Serializable {
    private static final long serialVersionUID = 1L;

    public SimpleBeforeAspect() {
    }

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        log.info("[b0x0 framework] do simple before aspect ! ");
    }
}