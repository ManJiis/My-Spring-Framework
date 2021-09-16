package top.b0x0.spring.framework.beans.factory;

import top.b0x0.spring.framework.beans.BeansException;

/**
 * bean工厂
 *
 * @author ManJiis
 * @since 2021-09-04
 * @since JDK1.8
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    Object getBean(String beanName, Object... args) throws BeansException;
}
