package top.b0x0.spring.mvc.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import top.b0x0.spring.mvc.framework.annotation.MyAutowired;
import top.b0x0.spring.mvc.framework.annotation.MyController;
import top.b0x0.spring.mvc.framework.annotation.MyRequestMapping;
import top.b0x0.spring.mvc.framework.annotation.MyRequestParam;
import top.b0x0.spring.mvc.framework.servlet.DispatcherServlet;
import top.b0x0.spring.mvc.service.ITestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK 1.8
 */
@MyRequestMapping("/testController")
@MyController("testController")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @MyAutowired("/testServiceImpl")
    public ITestService testService;

    @MyRequestMapping("/select")
    public void select(HttpServletRequest request, HttpServletResponse response,
                       @MyRequestParam("name") String name,
                       @MyRequestParam("age") String age) {

        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            String result = testService.select(name, age);
            printWriter.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
