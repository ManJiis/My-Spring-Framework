package top.b0x0.spring.framework.aop;

import lombok.extern.slf4j.Slf4j;
import top.b0x0.spring.framework.aop.advice.Advice;
import top.b0x0.spring.framework.aop.annotation.Aspect;
import top.b0x0.spring.framework.aop.annotation.AspectOrder;
import top.b0x0.spring.framework.beans.core.BeanContext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aop执行器
 *
 * @author ManJiis
 * @since 20121-08-26
 */
@Slf4j
public class Aop {

    /**
     * Bean容器
     */
    private final BeanContext beanContext;

    public Aop() {
        beanContext = BeanContext.getInstance();
    }

    /**
     * 执行Aop
     */
    public void doAop() {
        // 创建所有的代理通知列表
        List<ProxyAdvisor> proxyList = beanContext.getClassesBySuper(Advice.class)
                .stream()
                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
                .map(this::createProxyAdvisor)
                .collect(Collectors.toList());

        // 创建代理类并注入到Bean容器中
        beanContext.getClasses()
                .stream()
                .filter(clz -> !Advice.class.isAssignableFrom(clz))
                .filter(clz -> !clz.isAnnotationPresent(Aspect.class))
                .forEach(clz -> {
                    List<ProxyAdvisor> matchProxies = createMatchProxies(proxyList, clz);
                    if (matchProxies.size() > 0) {
                        Object proxyBean = CglibProxyCreator.createProxy(clz, matchProxies);
                        beanContext.addBean(clz, proxyBean);
                    }
                });
    }

    /**
     * 通过Aspect切面类创建代理通知类
     *
     * @param aspectClass Aspect切面类
     * @return 代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        int executeOrder = 0;
        if (aspectClass.isAnnotationPresent(AspectOrder.class)) {
            executeOrder = aspectClass.getAnnotation(AspectOrder.class).value();
        }
        String expression = aspectClass.getAnnotation(Aspect.class).pointcut();
        ProxyPointcut proxyPointcut = new ProxyPointcut();
        proxyPointcut.setExpression(expression);
        Advice advice = (Advice) beanContext.getBean(aspectClass);
        return new ProxyAdvisor(advice, proxyPointcut, executeOrder);
    }

    /**
     * 获取目标类匹配的代理通知列表
     *
     * @param proxyList   代理通知列表
     * @param targetClass 目标类
     * @return 匹配的代理通知列表
     */
    private List<ProxyAdvisor> createMatchProxies(List<ProxyAdvisor> proxyList, Class<?> targetClass) {
        Object targetBean = beanContext.getBean(targetClass);
        return proxyList
                .stream()
                .filter(advisor -> advisor.getPointcut().matches(targetBean.getClass()))
                .sorted(Comparator.comparingInt(ProxyAdvisor::getExecuteOrder))
                .collect(Collectors.toList());
    }

}
