package top.b0x0.spring.framework.beans.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.aop.annotation.Aspect;
import top.b0x0.spring.framework.common.util.ClassUtil;
import top.b0x0.spring.framework.ioc.annotation.Component;
import top.b0x0.spring.framework.ioc.annotation.Controller;
import top.b0x0.spring.framework.ioc.annotation.Repository;
import top.b0x0.spring.framework.ioc.annotation.Service;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Bean容器
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class BeanContext {
    private static final Logger log = LoggerFactory.getLogger(BeanContext.class);

    /**
     * 存放所有Bean的Map
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 是否加载Bean
     */
    private boolean isLoadBean = false;

    /**
     * 加载bean的注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class, Aspect.class);

    private BeanContext() {
    }

    private BeanContext(boolean isLoadBean) {
        this.isLoadBean = isLoadBean;
    }

    /**
     * 获取Bean容器实例
     *
     * @return BeanContainer
     */
    public static BeanContext getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 扫描加载所有Bean
     *
     * @param basePackage 包名
     */
    public void loadBeans(String basePackage) {
        if (isLoadBean()) {
            log.warn("bean has been loaded");
            return;
        }

        Set<Class<?>> classSet = ClassUtil.getPackageClass(basePackage);
        classSet.stream()
                .filter(clz -> {
                    for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                        if (clz.isAnnotationPresent(annotation)) {
                            return true;
                        }
                    }
                    return false;
                })
                .forEach(clz -> beanMap.put(clz, ClassUtil.newInstance(clz)));
        isLoadBean = true;
    }

    /**
     * 是否加载Bean
     *
     * @return 是否加载
     */
    public boolean isLoadBean() {
        return isLoadBean;
    }

    /**
     * 获取Bean实例
     *
     * @param clz Class类型
     * @return Bean实例
     */
    public Object getBean(Class<?> clz) {
        if (null == clz) {
            return null;
        }
        return beanMap.get(clz);
    }

    /**
     * 获取所有Bean集合
     *
     * @return Bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 添加一个Bean实例
     *
     * @param clz  Class类型
     * @param bean Bean实例
     * @return 原有的Bean实例, 没有则返回null
     */
    public Object addBean(Class<?> clz, Object bean) {
        return beanMap.put(clz, bean);
    }

    /**
     * 移除一个Bean实例
     *
     * @param clz Class类型
     */
    public void removeBean(Class<?> clz) {
        beanMap.remove(clz);
    }

    /**
     * Bean实例数量
     *
     * @return 数量
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 所有Bean的Class集合
     *
     * @return Class集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 通过注解获取Bean的Class集合
     *
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        return beanMap.keySet()
                .stream()
                .filter(clz -> clz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    /**
     * 通过实现类或者父类获取Bean的Class集合
     *
     * @param interfaceClass 接口Class或者父类Class
     * @return Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceClass) {
        return beanMap.keySet()
                .stream()
                .filter(interfaceClass::isAssignableFrom)
                .filter(clz -> !clz.equals(interfaceClass))
                .collect(Collectors.toSet());
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContext instance;

        ContainerHolder() {
            instance = new BeanContext();
        }
    }
}
