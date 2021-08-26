package top.b0x0.spring.framework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * cglib 代理类创建器
 *
 * @author ManJiis
 * @since 20121-08-26
 */
public final class CglibProxyCreator {

    /**
     * 创建代理类
     *
     * @param targetClass 目标类
     * @param proxyList   代理通知列表
     * @return 代理类
     */
    public static Object createProxy(Class<?> targetClass, List<ProxyAdvisor> proxyList) {
        return Enhancer.create(targetClass, new AdviceMethodInterceptor(targetClass, proxyList));
    }

    /**
     * cglib MethodInterceptor 实现类
     */
    private static class AdviceMethodInterceptor implements MethodInterceptor {

        /**
         * 目标类
         */
        private final Class<?> targetClass;

        /**
         * 代理通知列表
         */
        private final List<ProxyAdvisor> proxyAdvisorList;

        public AdviceMethodInterceptor(Class<?> targetClass, List<ProxyAdvisor> proxyAdvisorList) {
            this.targetClass = targetClass;
            this.proxyAdvisorList = proxyAdvisorList;
        }

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return new AdviceChain(targetClass, target, method, args, proxy, proxyAdvisorList).doAdviceChain();
        }
    }
}
