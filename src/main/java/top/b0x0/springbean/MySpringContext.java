package top.b0x0.springbean;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"top.b0x0.springbean"})
public class MySpringContext {

    public MySpringContext() {
        System.out.println("容器初始化中。。。。");
    }

}
