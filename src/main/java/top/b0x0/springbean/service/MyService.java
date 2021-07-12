package top.b0x0.springbean.service;

import top.b0x0.springbean.BeanPostProcessor;
import org.springframework.beans.BeansException;
import top.b0x0.springbean.demo.*;

@MyComponent("myService")
//@MyScope("prototype")
public class MyService implements BeanNameAware, InitializingBean, BeanPostProcessor {

    @MyAutowired
    private OrderService orderService;

    private String beanName;

    public MyService() {
    }

    public String getBeanName() {
        return beanName;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /**
     * 重写 InitializingBean 抽象方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用了afterPropertiesSet方法");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化之前运行" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化之后运行" + beanName);
        return bean;
    }

}
