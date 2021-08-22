package top.b0x0.spring.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.spring.framework.ioc.annotation.Autowired;
import top.b0x0.spring.framework.ioc.annotation.Controller;
import top.b0x0.spring.framework.webmvc.annotation.RequestMapping;
import top.b0x0.spring.framework.webmvc.annotation.RequestParam;
import top.b0x0.spring.sample.service.IMyTestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@RequestMapping("/myTestController")
@Controller("myTestController")
public class MyTestController {
    private static final Logger log = LoggerFactory.getLogger(MyTestController.class);

    @Autowired("MyTestServiceImpl")
    public IMyTestService testService;

    @RequestMapping("/select")
    public void select(@RequestParam("name") String name, @RequestParam("age") String age) {
        System.out.println("age = " + age);
        System.out.println("name = " + name);
    }
}
