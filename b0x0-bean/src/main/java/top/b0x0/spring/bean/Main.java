package top.b0x0.spring.bean;

import top.b0x0.spring.bean.demo.MyApplicationContext;
import top.b0x0.spring.bean.service.MyService;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TANG
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 1. 使用spring容器
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MySpringContext.class);
//        MyBean myBean = (MyBean) context.getBean("myBean");
//        myBean.test();

        // 2. 手写实现Spring容器
        MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);
        ConcurrentHashMap<String, Object> singletonMap = myApplicationContext.getSingletonMap();
        System.out.println("singletonMap = " + singletonMap);
        MyService myService = (MyService) myApplicationContext.getBean("myService");
        System.out.println(myService.getBeanName());
        System.out.println(myService.getOrderService());

        // 3. 测试单例bean和原型bean
       /* System.out.println(myApplicationContext.getBean("myService"));
        System.out.println(myApplicationContext.getBean("myService"));
        System.out.println(myApplicationContext.getBean("myService"));

        System.out.println("--------------以下是单例bean测试结果------------");

        System.out.println(myApplicationContext.getBean("myService1"));
        System.out.println(myApplicationContext.getBean("myService1"));
        System.out.println(myApplicationContext.getBean("myService1"));
        */


        // 验证单例 原型
        /*
           System.out.println(Singleton.getSingleton());
           System.out.println(Singleton.getSingleton());
           System.out.println(Singleton.getSingleton());
        */
    }
}
