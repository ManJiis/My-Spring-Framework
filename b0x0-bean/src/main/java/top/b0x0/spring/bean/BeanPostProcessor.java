package top.b0x0.spring.bean;


import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * Bean后置处理器
 *
 * @author TANG
 */
public interface BeanPostProcessor {

    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
