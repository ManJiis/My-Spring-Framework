package top.b0x0.spring.framework.beans.factory.config;

import top.b0x0.spring.framework.beans.PropertyValues;

/**
 * 实例定义
 *
 * @author ManJiis
 * @since 2021-09-04
 * @since JDK1.8
 */
public class BeanDefinition<T> {
    private Class<T> beanClass;
    private PropertyValues propertyValues;

    public BeanDefinition() {
    }

    public BeanDefinition(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition(Class<T> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
