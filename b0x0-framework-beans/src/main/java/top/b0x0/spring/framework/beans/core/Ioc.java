package top.b0x0.spring.framework.beans.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.common.util.ClassUtil;
import top.b0x0.spring.framework.ioc.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Ioc执行器
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class Ioc {
    private static final Logger log = LoggerFactory.getLogger(Ioc.class);

    /**
     * Bean容器
     */
    private final BeanContext beanContext;

    public Ioc() {
        beanContext = BeanContext.getInstance();
    }

    /**
     * 执行Ioc
     */
    public void doInstance() {
        for (Class<?> clz : beanContext.getClasses()) {
            final Object targetBean = beanContext.getBean(clz);
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    final Class<?> fieldClass = field.getType();
                    Object fieldValue = getClassInstance(fieldClass);
                    if (null != fieldValue) {
                        ClassUtil.setField(field, targetBean, fieldValue);
                    } else {
                        throw new RuntimeException("无法注入对应的类，目标类型:" + fieldClass.getName());
                    }
                }
            }
        }
    }

    /**
     * 根据Class获取其实例或者实现类
     *
     * @param clz Class
     * @return 实例或者实现类
     */
    private Object getClassInstance(final Class<?> clz) {
        return Optional
                .ofNullable(beanContext.getBean(clz))
                .orElseGet(() -> {
                    Class<?> implementClass = getImplementClass(clz);
                    if (null != implementClass) {
                        return beanContext.getBean(implementClass);
                    }
                    return null;
                });
    }

    /**
     * 获取接口的实现类
     *
     * @param interfaceClass 接口
     * @return 实现类
     */
    private Class<?> getImplementClass(final Class<?> interfaceClass) {
        return beanContext.getClassesBySuper(interfaceClass)
                .stream()
                .findFirst()
                .orElse(null);
    }

}
