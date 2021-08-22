package top.b0x0.spring.sample.controller;

import top.b0x0.spring.framework.ioc.annotation.Controller;
import top.b0x0.spring.framework.webmvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK1.8
 */
@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("/")
    public void index() {
        System.out.println("Hello ManJi .");
    }
}
