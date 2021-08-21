package top.b0x0.spring.sample.mvc.controller;

import top.b0x0.spring.framework.webmvc.annotation.Controller;
import top.b0x0.spring.framework.webmvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author ManJiis
 * @since 2021-08-21
 * @since JDK1.8
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            printWriter.write("Hello ManJi.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
