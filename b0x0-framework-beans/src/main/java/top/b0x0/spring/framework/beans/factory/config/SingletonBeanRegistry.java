package top.b0x0.spring.framework.beans.factory.config;

/**
 * 单例注册
 *
 * @author ManJiis
 * @since 2021-09-16
 * @since JDK1.8
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);
}
