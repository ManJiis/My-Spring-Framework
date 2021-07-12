package top.b0x0.springbean;

import org.springframework.stereotype.Component;

@Component
public class MyBean {
    public void  test(){
        System.out.println("执行test方法");
    }
}