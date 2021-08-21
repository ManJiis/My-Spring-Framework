//package top.b0x0.spring.framework.webmvc.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import top.b0x0.spring.framework.webmvc.annotation.Autowired;
//import top.b0x0.spring.framework.webmvc.annotation.Controller;
//import top.b0x0.spring.framework.webmvc.annotation.RequestMapping;
//import top.b0x0.spring.framework.webmvc.annotation.RequestParam;
//import top.b0x0.spring.framework.webmvc.service.ITestService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @author ManJiis
// * @since 2021-08-21
// * @since JDK 1.8
// */
//@RequestMapping("/testController")
//@Controller("testController")
//public class TestController {
//    private static final Logger log = LoggerFactory.getLogger(TestController.class);
//
//    @Autowired("/testServiceImpl")
//    public ITestService testService;
//
//    @RequestMapping("/select")
//    public void select(HttpServletRequest request, HttpServletResponse response,
//                       @RequestParam("name") String name,
//                       @RequestParam("age") String age) {
//
//        PrintWriter printWriter;
//        try {
//            printWriter = response.getWriter();
//            String result = testService.select(name, age);
//            printWriter.write(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
